package com.example.dopostemail.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    Boolean isLogged = false;
    String Username;
    public static final String LOGGED_USERNAME = "username";
    public static final String LOGGED_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText username = findViewById(R.id.usernameLogin);
        EditText password = findViewById(R.id.passwordLogin);
        username.setText("user");
        password.setText("user");


    }

    private void doLogin( final View v){
        final EditText username = findViewById(R.id.usernameLogin);
        EditText password = findViewById(R.id.passwordLogin);
        final String u = username.getText().toString();
        final String p = password.getText().toString();

        User tempUser = new User();
        tempUser.setUsername(u);
        tempUser.setPassword(p);

        LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);

        Call<User> callLogin = service.doLogin(tempUser);

        callLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

//                if(user.getAccounts().isEmpty()){
//                    Toast.makeText(LoginActivity.this, "Empty in loggin", Toast.LENGTH_SHORT).show();
//                }

                Intent i = new Intent(LoginActivity.this, PickEmailActivity.class);
                startActivity(i);

                Gson gson = new Gson();
                String json = gson.toJson(user);

                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userObject", json);
                editor.putString("loggedInUser", user.getUsername());
                editor.putLong("userId", user.getId());
                editor.apply();
                editor.commit();
                Toast.makeText(LoginActivity.this, "Good credentials!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
    }



    public void showProgress(View view) {
        final int THREE_SECONDS = 4*1000;
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setMessage("Loading...");
        dlg.setCancelable(false);
        dlg.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dlg.dismiss();
            }
        }, THREE_SECONDS);

        doLogin(view);
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
