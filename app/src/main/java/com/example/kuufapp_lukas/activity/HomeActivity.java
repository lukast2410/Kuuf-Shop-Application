package com.example.kuufapp_lukas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuufapp_lukas.R;
import com.example.kuufapp_lukas.adapter.TransactionAdapter;
import com.example.kuufapp_lukas.model.Transaction;
import com.example.kuufapp_lukas.util.HelperData;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements TransactionAdapter.TransactionClickListener {
    Toolbar toolbar;
    RecyclerView rvHistory;
    TransactionAdapter adapter;
    ArrayList<Transaction> transactions;
    TextView tvHistoryEmpty;
    ScrollView svHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeToolbar();
        setListTransactionHistory();
        refreshHistory();
    }

    private void refreshHistory() {
        if(transactions.isEmpty()){
            tvHistoryEmpty.setVisibility(View.VISIBLE);
            svHistory.setVisibility(View.GONE);
        }else{
            tvHistoryEmpty.setVisibility(View.GONE);
            svHistory.setVisibility(View.VISIBLE);
            rvHistory.invalidate();
        }
    }

    private void setListTransactionHistory() {
        tvHistoryEmpty = (TextView) findViewById(R.id.tv_no_history);
        svHistory = (ScrollView) findViewById(R.id.sv_history);
        rvHistory = (RecyclerView) findViewById(R.id.rv_history);
        transactions = getCurrentUserTransactionHistory();
        adapter = new TransactionAdapter(transactions, this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(adapter);
    }

    private ArrayList<Transaction> getCurrentUserTransactionHistory() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction tr: HelperData.getInstance().getTransactions()) {
            if(tr.getUserId() == HelperData.getInstance().getCurrentUser().getId()){
                transactions.add(tr);
            }
        }
        return transactions;
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                return true;
            case R.id.menu_store:
                goToActivity("store");
                return true;
            case R.id.menu_profile:
                goToActivity("profile");
                return true;
            case R.id.menu_logout:
                goToActivity("logout");
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onDeleteClicked(int position) {
        Transaction transaction = transactions.get(position);
        transactions.remove(transaction);
        HelperData.getInstance().getTransactions().remove(transaction);
        adapter.notifyItemRemoved(position);
        refreshHistory();
        saveTransactionsToSharedPreferences();
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
}