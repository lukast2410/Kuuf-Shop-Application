package com.example.kuufapp_lukas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuufapp_lukas.R;
import com.example.kuufapp_lukas.model.Product;
import com.example.kuufapp_lukas.model.Transaction;
import com.example.kuufapp_lukas.model.User;
import com.example.kuufapp_lukas.util.HelperData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout layout;
    EditText etUsername, etPassword;
    TextView errUsername, errPassword, errLogin;
    Button btnLogin, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getLocalData();
        initializeBackground();
        initializeLoginForm();
        setInputForm();
        setButton();
    }

    private void initializeBackground() {
        layout = (RelativeLayout) findViewById(R.id.login_layout);
        AnimationDrawable animBg = (AnimationDrawable) layout.getBackground();
        animBg.setEnterFadeDuration(3000);
        animBg.setExitFadeDuration(4000);
        animBg.start();
    }

    private void initializeLoginForm() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        errUsername = (TextView) findViewById(R.id.tv_username_empty);
        errPassword = (TextView) findViewById(R.id.tv_password_empty);
        errLogin = (TextView) findViewById(R.id.tv_login_failed);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnCreateAccount = (Button) findViewById(R.id.btn_create_account);
    }

    private void setButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean empty = false;
                if(etUsername.getText().toString().equals("")){
                    errUsername.setVisibility(View.VISIBLE);
                    empty = true;
                }else{
                    errUsername.setVisibility(View.GONE);
                }

                if(etPassword.getText().toString().equals("")){
                    errPassword.setVisibility(View.VISIBLE);
                    empty = true;
                }else{
                    errPassword.setVisibility(View.GONE);
                }

                if(empty)
                    return;

                if(userExists(etUsername.getText().toString(), etPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                    errLogin.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    errLogin.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean userExists(String username, String password) {
        for(User u : HelperData.getInstance().getUsers()){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                HelperData.getInstance().setCurrentUser(u);
                return true;
            }
        }
        return false;
    }

    private void setInputForm() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(etUsername.getText().toString().equals("")){
                    errUsername.setVisibility(View.VISIBLE);
                }else{
                    errUsername.setVisibility(View.GONE);
                }

                if(etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("")){
                    btnLogin.setEnabled(false);
                }else{
                    btnLogin.setEnabled(true);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(etPassword.getText().toString().equals("")){
                    errPassword.setVisibility(View.VISIBLE);
                }else{
                    errPassword.setVisibility(View.GONE);
                }

                if(etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("")){
                    btnLogin.setEnabled(false);
                }else{
                    btnLogin.setEnabled(true);
                }
            }
        });
    }

    private void getLocalData() {
        SharedPreferences sp = getSharedPreferences(HelperData.KUUF, MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sp.getString(HelperData.USER_DATA, null);
        if(json == null || json.isEmpty()){
            HelperData.getInstance().setUsers(new ArrayList<>());
        }else{
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            HelperData.getInstance().setUsers(gson.fromJson(json, type));
        }

        json = sp.getString(HelperData.PRODUCT_DATA, null);
        if(json == null || json.isEmpty()){
            ArrayList<Product> products = new ArrayList<>();
            products.add(new Product(1, "Exploding Kitten", 2, 5,
                    250000, 106.265139, -6.912035));
            products.add(new Product(2, "Card Against Humanity", 2, 4,
                    182500, 108.126810, -7.586037));
            HelperData.getInstance().setProducts(products);
        }else{
            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            HelperData.getInstance().setProducts(gson.fromJson(json, type));
        }

        json = sp.getString(HelperData.TRANSACTION_DATA, null);
        if(json == null || json.isEmpty()){
            HelperData.getInstance().setTransactions(new ArrayList<>());
        }else{
            Type type = new TypeToken<ArrayList<Transaction>>(){}.getType();
            HelperData.getInstance().setTransactions(gson.fromJson(json, type));
        }
    }
}