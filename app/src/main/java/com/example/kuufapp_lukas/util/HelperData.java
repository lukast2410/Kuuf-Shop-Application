package com.example.kuufapp_lukas.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kuufapp_lukas.model.Product;
import com.example.kuufapp_lukas.model.Transaction;
import com.example.kuufapp_lukas.model.User;

import java.util.ArrayList;

public class HelperData {
    public static final String KUUF = "Kuuf";
    public static final String USER_DATA = "USER_DATA";
    public static final String PRODUCT_DATA = "PRODUCT_DATA";
    public static final String TRANSACTION_DATA = "TRANSACTION_DATA";

    private static HelperData data = null;
    private ArrayList<User> users;
    private ArrayList<Product> products;
    private ArrayList<Transaction> transactions;
    private User currentUser;

    private HelperData() { }

    public static HelperData getInstance() {
        return data = (data == null) ? new HelperData() : data;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
