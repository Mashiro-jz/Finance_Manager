package com.finance_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private  UUID id;
    BigDecimal amount;
    LocalDate date;
    String description;
    TransactionType transactionType;
    TransactionCategory transactionCategory;

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public Transaction(UUID id, BigDecimal amount, LocalDate date, String description, TransactionType transactionType, TransactionCategory transactionCategory) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionCategory = transactionCategory;
    }
    public Transaction(){};

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", transactionType=" + transactionType +
                ", transactionCategory=" + transactionCategory +
                '}';
    }

}
