package com.example.kuufapp_lukas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kuufapp_lukas.R;
import com.example.kuufapp_lukas.model.Product;
import com.example.kuufapp_lukas.model.Transaction;
import com.example.kuufapp_lukas.model.User;
import com.example.kuufapp_lukas.util.HelperData;
import com.google.gson.Gson;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ProductDetailActivity extends AppCompatActivity {
    Product product;

    Toolbar toolbar;
    TextView tvName, tvPrice, tvMinMax;
    Button btnBuy, btnOk;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getDataFromIntent();
        initializeToolbar();
        initializeComponents();
        setComponent();
        setDialog();
        setBuyProduct();
    }

    private void setBuyProduct() {
        btnBuy.setOnClickListener(x -> {
            User curr = HelperData.getInstance().getCurrentUser();
            if(curr.getWallet() < product.getPrice()){
                dialog.show();
            }else{
                curr.setWallet(curr.getWallet() - product.getPrice());
                saveUsersToSharedPreferences();
                HelperData.getInstance().getTransactions().add(new Transaction(
                        getTransactionLastId() + 1,
                        curr.getId(),
                        product.getId(),
                        new Date()
                ));
                saveTransactionsToSharedPreferences();

                goToActivity("home");
            }
        });
    }

    private int getTransactionLastId() {
        ArrayList<Transaction> transactions = HelperData.getInstance().getTransactions();
        if(!transactions.isEmpty()){
            return transactions.get(transactions.size()-1).getId();
        }
        return 0;
    }

    private void saveTransactionsToSharedPreferences() {
        SharedPreferences sp = getSharedPreferences(HelperData.KUUF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        ArrayList<Transaction> transactions = HelperData.getInstance().getTransactions();

        Gson gson = new Gson();
        String json = gson.toJson(transactions);
        editor.putString(HelperData.TRANSACTION_DATA, json);
        editor.apply();
    }

    private void saveUsersToSharedPreferences() {
        SharedPreferences sp = getSharedPreferences(HelperData.KUUF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        ArrayList<User> users = HelperData.getInstance().getUsers();

        Gson gson = new Gson();
        String json = gson.toJson(users);
        editor.putString(HelperData.USER_DATA, json);
        editor.apply();
    }

    private void setDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.buy_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(x -> {
            dialog.dismiss();
        });
    }

    private void setComponent() {
        tvName.setText(product.getName());
        tvPrice.setText(HelperData.getPriceString(product.getPrice()));
        String player = product.getMinPlayer() + " - " + product.getMaxPlayer() + " Players";
        tvMinMax.setText(player);
    }

    private void initializeComponents() {
        tvName = (TextView) findViewById(R.id.tv_detail_name);
        tvPrice = (TextView) findViewById(R.id.tv_detail_price);
        tvMinMax = (TextView) findViewById(R.id.tv_detail_min_max);
        btnBuy = (Button) findViewById(R.id.btn_buy);
    }

    private void getDataFromIntent() {
        product =  (Product) getIntent().getSerializableExtra(HelperData.PRODUCT_DATA);
        if(product == null){
            onBackPressed();
        }
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void goToActivity(String act) {
        Intent intent = new Intent(this, ProfileActivity.class);
        if(act.equals("home")){
            intent.setClass(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }else if(act.equals("store")){
            intent.setClass(this, StoreActivity.class);
        }else if(act.equals("logout")){
            HelperData.getInstance().setCurrentUser(null);
            intent.setClass(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}