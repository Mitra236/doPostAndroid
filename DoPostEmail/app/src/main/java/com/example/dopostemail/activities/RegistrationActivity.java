package com.example.dopostemail.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.firstName);
        lastname= findViewById(R.id.lastNameRegister);
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);

        String f = firstname.getText().toString();
        String l = lastname.getText().toString();
        String u = username.getText().toString();
        String p = password.getText().toString();

        User tempUser = new User();
        tempUser.setFirstname("u");
        tempUser.setLastname("u");
        tempUser.setUsername("u");
        tempUser.setPassword("u");

        Log.i("USEEEEEEEEEEEEEEEEER", firstname.getText().toString());

        LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);

        final Call<User> callRegister = service.register(tempUser);

        Button btnRegister = findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callRegister.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();

                        if(user != null) {
                            Toast.makeText(RegistrationActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegistrationActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });





    }

//    private void register( final View v){
//        final EditText firstname = findViewById(R.id.firstNameRegister);
//        final EditText lastname = findViewById(R.id.lastNameRegister);
//        final EditText username = findViewById(R.id.usernameRegister);
//        final EditText password = findViewById(R.id.passwordRegister);
//
//        final String f = firstname.getText().toString();
//        final String l = lastname.getText().toString();
//        final String u = username.getText().toString();
//        final String p = password.getText().toString();
//
//        User tempUser = new User();
//        tempUser.setFirstname(f);
//        tempUser.setLastname(l);
//        tempUser.setUsername(u);
//        tempUser.setPassword(p);
//
//        LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);
//
//        Call<User> callLogin = service.register(tempUser);
//
//        callLogin.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//
//                if(user != null) {
//                    Toast.makeText(RegistrationActivity.this, "Successful regiistration", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(RegistrationActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void showProgress(View view) {
//        final int THREE_SECONDS = 4*1000;
//        final ProgressDialog dlg = new ProgressDialog(this);
//        dlg.setMessage("Loading...");
//        dlg.setCancelable(false);
//        dlg.show();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                dlg.dismiss();
//            }
//        }, THREE_SECONDS);
//
//        register(view);
//    }

    @Override
    protected void onStart(){
        super.onStart();
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
