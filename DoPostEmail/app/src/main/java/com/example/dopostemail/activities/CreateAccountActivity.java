package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Format;
import com.example.dopostemail.model.Photo;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.AccountInterface;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Create contact");
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_create_profile);
        setSupportActionBar(toolbar);





        Button btnCreate = findViewById(R.id.button_account_create);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtUsername = findViewById(R.id.textUserName);
                TextView txtPass = findViewById(R.id.textPass);
                TextView txtPassRepeat = findViewById(R.id.repeatPasswordEdit);
                TextView txtEmail = findViewById(R.id.emailEdit);
                String newUsername = "";
                String newPassword = "";
                String newPasswordRepeat = "";
                String newEmail = "";
                newUsername = txtUsername.getText().toString();
                newPassword = txtPass.getText().toString();
                newPasswordRepeat = txtPass.getText().toString();
                newEmail = txtEmail.getText().toString();

                if (TextUtils.isEmpty(newUsername)) {
                    txtUsername.setError("Username required");
                    txtUsername.requestFocus();
                } else if (TextUtils.isEmpty(newPassword)) {
                    txtPass.setError("Password required");
                    txtPass.requestFocus();
                } else if (TextUtils.isEmpty(newPasswordRepeat)) {
                    txtPassRepeat.setError("Password required");
                    txtPassRepeat.requestFocus();
                } else if (!newPassword.equals(newPasswordRepeat)) {
                    txtPassRepeat.setError("Passwords must match");
                    txtPassRepeat.requestFocus();
                } else if (TextUtils.isEmpty(newEmail)) {
                    txtEmail.setError("Email required");
                    txtEmail.requestFocus();
                } else if (TextUtils.isEmpty(newEmail) || !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                    txtEmail.setError(getString(R.string.edit_email));
                    txtEmail.requestFocus();
                } else {

                    RadioButton formatHTML = findViewById(R.id.radioHTML);


                    AccountInterface service = RetrofitClient.getClient().create(AccountInterface.class);
                    Account newAcc = new Account();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json = pref.getString("userObject", "");

                    Gson gson = new Gson();
                    final User loggedInUser = gson.fromJson(json, User.class);

                    short type = 3;

                    newAcc.setUsername(newUsername);
                    newAcc.setPassword(newPassword);
                    newAcc.setUser(loggedInUser);
                    newAcc.setDisplayname(newEmail);
                    newAcc.setSmtp_address("smpt1");
                    newAcc.setSmtp_port(8080);
                    newAcc.setInserver_address("pop3");
                    newAcc.setInserver_port(3306);
                    newAcc.setInserver_type(type);

                    Call<Account> call = service.addAccount(newAcc);

                    call.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            Toast.makeText(CreateAccountActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateAccountActivity.this, PickEmailActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            Toast.makeText(CreateAccountActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


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
