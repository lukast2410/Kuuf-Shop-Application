package com.example.kuufapp_lukas.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public static String getPriceString(int price){
        String priceTxt = price + "";
        Log.d("cekPrice", priceTxt.length() + "");
        int divide = priceTxt.length() / 3;
        int module = priceTxt.length() % 3;

        String result = "" + priceTxt.charAt(0);
        for (int i=1;i<priceTxt.length();i++){
            if(i == module-1 || (i+1-module) % 3 == 0 && i != priceTxt.length()-1){
                result += (priceTxt.charAt(i) + ".");
            }else{
                result += priceTxt.charAt(i);
            }
            Log.d("cekPrice" + i, result);
        }
        return result;
    }
}
