package com.example.transaction.repository;

import com.example.transaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TransactionDaoImpl implements TransactionDao{

    private final  Map<Long, Transaction> transactions;

    private TransactionDaoImpl() {
        transactions = new HashMap<>();
    }

    @Override
    public Transaction putTransaction(Transaction transaction, long id) {
        transaction.setTransaction_id(id);
        //Si ingresa no ingresa el parent_id significa que es una transaccion padre, entonces la agregamos al map
        if (transaction.getParent_id() == null){
            transactions.put(id,transaction);
        }else{
            //trae la transaccion padre, si no existe devuelve null y si existe le agrega a su listaHijos la transaccion actual
            Transaction tFather = transactions.get(transaction.getParent_id());
            transactions.put(id,transaction);
            if (tFather == null){
                return null;
            }else{
                if(tFather.getChildTransaction() == null){
                    tFather.setChildTransaction(new ArrayList<>());
                }
                tFather.getChildTransaction().add(transaction);
            }
        }
        return transaction;
    }


    @Override
    public List<Long> getType(String type) {
        return transactions.values().stream()
                .filter(trans -> trans.getType().equals(type))
                .map(id -> id.getTransaction_id())
                .collect(Collectors.toList());
    }

    @Override
    public Double getSum(Long id) {
        Transaction tFather = transactions.get(id);
        if (tFather == null) {
            return null;
        } else {
            if(tFather.getChildTransaction() == null){
                return tFather.getAmount();
            }else{
                return tFather.getChildTransaction().stream()
                        .mapToDouble(Transaction::getAmount)
                        .reduce(tFather.getAmount(),(sum,sumNext) -> sum+sumNext);
            }
        }
    }
}
