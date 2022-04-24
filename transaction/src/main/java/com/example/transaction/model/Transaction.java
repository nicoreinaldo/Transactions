package com.example.transaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

public class Transaction {

    private Long transaction_id;
    private Double amount;
    private String Type;
    private Long parent_id;
    private List<Transaction> childTransaction;

    public Transaction() {
    }

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public List<Transaction> getChildTransaction() {
        return childTransaction;
    }

    public void setChildTransaction(List<Transaction> childTransaction) {
        this.childTransaction = childTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transaction_id.equals(that.transaction_id) && amount.equals(that.amount) && Type.equals(that.Type) && parent_id.equals(that.parent_id) && childTransaction.equals(that.childTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction_id, amount, Type, parent_id, childTransaction);
    }
}
