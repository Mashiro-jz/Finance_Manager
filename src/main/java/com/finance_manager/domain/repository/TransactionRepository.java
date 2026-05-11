package com.finance_manager.domain.repository;

import com.finance_manager.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    void deleteById(UUID id);
    List<Transaction> findAll();
}
