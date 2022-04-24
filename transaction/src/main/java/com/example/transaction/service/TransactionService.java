package com.example.transaction.service;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public Map<String, String> setStatus(String value) {
        Map<String, String> status = new HashMap<>();
        status.put("status", value);
        return status;
    }

    public Map<String, Double> setStatusSum(Double value) {
        Map<String, Double> status = new HashMap<>();
        status.put("sum", value);
        return status;
    }

    public ResponseEntity validatorPut(Transaction transaction){
        if(transaction.getType().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setStatus("El tipo no puede estar vacio"));
        }
        if(transaction.getAmount()<0){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setStatus("El monto para la transaccion debe ser positivo"));
        }
        return ResponseEntity.ok("ok");
    }

    public Transaction putTransaction(Transaction transaction, long id) {
        return  transactionDao.putTransaction(transaction,id);
    }

    public List<Long> getTransactionByType(String type){
        return  transactionDao.getType(type);
    }

    public Map<String, Double>  getTransactionById(Long transactionId){
        Double result = transactionDao.getSum(transactionId);
        return setStatusSum(result);
    }
}
