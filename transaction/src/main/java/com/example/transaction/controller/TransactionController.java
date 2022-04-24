package com.example.transaction.controller;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionDao;
import com.example.transaction.repository.TransactionDaoImpl;
import com.example.transaction.service.TransactionService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private  TransactionService service;

    @RequestMapping(value = "/{transaction_id}",method = RequestMethod.PUT)
    public ResponseEntity putTransaction(@PathVariable("transaction_id") long transactionId, @RequestBody Transaction transaction){
        ResponseEntity validation = service.validatorPut(transaction);
        if (validation.getStatusCode().isError()){
            return validation;
        }else{
            Transaction save = service.putTransaction(transaction,transactionId);
            if(save == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.setStatus("Error al guardar la transaccion"));
            }else{
                return ResponseEntity.ok(service.setStatus("ok"));
            }
        }
    }

    @RequestMapping(value = "/types/{type}",method = RequestMethod.GET)
    public ResponseEntity<List<Long>> getTransactionType(@PathVariable("type") String type){
        if(type.isBlank()){
            return null;
        }else{
            return ResponseEntity.ok(service.getTransactionByType(type));
        }
    }

    @RequestMapping(value = "sum/{transaction_id}",method = RequestMethod.GET)
    public ResponseEntity getTransactionSum(@PathVariable("transaction_id") long transactionId){
        return  ResponseEntity.ok(service.getTransactionById(transactionId));
    }



}
