package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ChangePersonalDataActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setTitle("Change Password");
        setContentView(R.layout.activity_change_personal_data);

        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = prefs.getString("userObject", "");

        Gson gson = new Gson();
        final User loggedInUser = gson.fromJson(json, User.class);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        final EditText username = findViewById(R.id.username);

        firstName.setText(loggedInUser.getFirstname());
        lastName.setText(loggedInUser.getLastname());
        username.setText(loggedInUser.getUsername());


        Button btnSave = findViewById(R.id.btn_change_pass);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String np = firstName.getText().toString();
                String rp = lastName.getText().toString();
                String u  = username.getText().toString();


                    user = new User();
                    user.setFirstname(np);
                    user.setLastname(rp);
                    user.setUsername(u);
                    user.setPassword(loggedInUser.getPassword());

                    LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);

                    Call<User> callLogin = service.changePersonalData(user, loggedInUser.getId());

                    callLogin.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(ChangePersonalDataActivity.this, "Successfully changed", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ChangePersonalDataActivity.this, LoginActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("WHYYY THE FUCK", t.getMessage());
                            Toast.makeText(ChangePersonalDataActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


        });
    }

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
