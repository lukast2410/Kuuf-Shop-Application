package com.example.kuufapp_lukas.model;

import java.util.Date;

public class Transaction {
    private int id;
    private int userId;
    private int productId;
    private Date transactionDate;

    public Transaction(int id, int userId, int productId, Date transactionDate) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
