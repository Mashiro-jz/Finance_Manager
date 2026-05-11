package com.finance_manager.infrastructure.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finance_manager.domain.model.Transaction;
import com.finance_manager.domain.repository.TransactionRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonTransactionRepository implements TransactionRepository {
    String filePath ="src\\transaction.json";
    private final ObjectMapper mapper;

    public JsonTransactionRepository() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private List<Transaction> readFromFile() {
        File file = new File(filePath);
        if(!file.exists() || file.length() == 0){
            return new ArrayList<>();
        }
        else {
            try {
                return mapper.readValue(file, new TypeReference<List<Transaction>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    private void writeToFile(List<Transaction> list) {
        try{
            File file = new File(filePath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Transaction save(Transaction transaction) {
        List<Transaction> list = readFromFile();
        list.add(transaction);
        System.out.println("Saving transaction: " + transaction.getDescription() + " | Total: " + list.size());
        writeToFile(list);
        return transaction;
    }

    @Override
    public void deleteById(UUID id) {
        List<Transaction> list = readFromFile();
        list.removeIf(t -> t.getId().equals(id));
        writeToFile(list);
    }

    @Override
    public List<Transaction> findAll() {
        return readFromFile();
    }
}
