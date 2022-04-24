package com.example.transaction.controller;

import com.example.transaction.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TransactionControllerWebTestClientTest {

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testTransaction() throws JsonProcessingException {
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(5000.0);
        transaction.setType("cars");

        Map<String, String> status = new HashMap<>();
        status.put("status","ok");

        //when
        client.put().uri("/transactions/10")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transaction)
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("ok")
                .json(objectMapper.writeValueAsString(status));

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(10000.0);
        transaction2.setType("shopping");
        transaction2.setParent_id(10L);

        client.put().uri("/transactions/11")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transaction2)
                .exchange()
                //then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("ok")
                .json(objectMapper.writeValueAsString(status));
    }

    @Test
    void testType(){
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(10L);
        transaction.setAmount(5000.0);
        transaction.setType("cars");

        Transaction transaction2 = new Transaction();
        transaction2.setTransaction_id(11L);
        transaction2.setAmount(10000.0);
        transaction2.setType("shopping");
        transaction2.setParent_id(10L);

        List<Long> listId = new ArrayList<>();
        listId.add(10L);

        client.get().uri("/transactions/types/cars").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .equals(listId);
    }

    @Test
    void testSum(){

        Transaction transaction = new Transaction();
        transaction.setTransaction_id(10L);
        transaction.setAmount(5000.0);
        transaction.setType("cars");

        Transaction transaction2 = new Transaction();
        transaction2.setTransaction_id(11L);
        transaction2.setAmount(10000.0);
        transaction2.setType("shopping");
        transaction2.setParent_id(10L);

        Map<String, Double> statusSum = new HashMap<>();
        Map<String, Double> statusSum2 = new HashMap<>();
        statusSum.put("sum",15000.0);
        statusSum2.put("sum",10000.0);
        Double sum = transaction.getAmount() + transaction2.getAmount();

        client.get().uri("/transactions/sum/10").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .equals(statusSum);

        client.get().uri("/transactions/sum/11").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .equals(statusSum2);
    }
}