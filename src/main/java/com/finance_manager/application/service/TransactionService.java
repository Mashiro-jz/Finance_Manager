package com.finance_manager.application.service;

import com.finance_manager.domain.model.Transaction;
import com.finance_manager.domain.model.TransactionCategory;
import com.finance_manager.domain.model.TransactionType;
import com.finance_manager.domain.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(BigDecimal amount, String description, TransactionType transactionType, TransactionCategory transactionCategory) {
        Transaction transaction = new Transaction(UUID.randomUUID(), amount, LocalDate.now(), description, transactionType, transactionCategory);
        transactionRepository.save(transaction);
    }

    public void removeTransaction(UUID id){
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getSortedByDate(){
        return transactionRepository.findAll();
    }

    public BigDecimal getBalance(){
        List<Transaction> allTransactions = transactionRepository.findAll();

        return allTransactions.stream()
                .map(t -> t.getTransactionType() == TransactionType.INCOME ? t.getAmount() : t.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionCategory, BigDecimal> getOutgoesByCategory(){
        List<Transaction> allTransactions = transactionRepository.findAll();

        return allTransactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.OUTGO)
                .collect(Collectors.groupingBy(
                        Transaction::getTransactionCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));
    }

    public Optional<Transaction> getHighestOutgoInCurrentMonth(int month, int year){
        List<Transaction> allTransactions = transactionRepository.findAll();

        return allTransactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.OUTGO)
                .filter(t -> t.getDate().getYear() == year)
                .filter(t -> t.getDate().getMonth().getValue() == month)
                .max(Comparator.comparing(Transaction::getAmount));
    }
}
