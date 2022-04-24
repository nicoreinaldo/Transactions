package com.example.transaction.repository;

import com.example.transaction.model.Transaction;

import java.util.List;

public interface TransactionDao {

    Transaction putTransaction(Transaction transaction, long id);

    List<Long> getType(String type);

    Double getSum(Long id);

}
