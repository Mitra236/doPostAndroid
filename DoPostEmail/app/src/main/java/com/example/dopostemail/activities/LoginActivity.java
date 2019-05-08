package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Boolean isLogged = false;
    String Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnEmails = (Button) findViewById(R.id.button_login);
        btnEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(v);

            }
        });
    }

    private void doLogin( final View v){
        LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);
        Call<ArrayList<Account>> call = service.getAccounts();

        EditText username = findViewById(R.id.usernameLogin);
        EditText password = findViewById(R.id.passwordLogin);
        final String u = username.getText().toString();

        final String p = password.getText().toString();


        call.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                ArrayList<Account> accounts = response.body();

                for(Account a : accounts){
                    if(a.getUsername().equals(u) && a.getPassword().equals(p)){
                        isLogged = true;
                        Username = a.getUsername();
                        btnStartEmailsActivity(v);

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        Toast.makeText(LoginActivity.this, "Good credentials!", Toast.LENGTH_SHORT).show();

                   }

                }
                if(isLogged == false){
                    Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    public void btnStartEmailsActivity(View v){
        Intent i = new Intent(LoginActivity.this, EmailsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume(){
        super.onResume();



    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
