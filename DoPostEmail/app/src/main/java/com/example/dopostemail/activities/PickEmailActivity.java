package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.AccountAdapter;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickEmailActivity extends AppCompatActivity {

    ListView mListView;
    private AccountAdapter adapter;
    private List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Accounts");
        setContentView(R.layout.activity_pick_email);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = pref.getString("userObject", "");

        Gson gson = new Gson();
        final User loggedInUser = gson.fromJson(json, User.class);

        Toolbar toolbar = findViewById(R.id.toolbar_pick);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.list_view);
        Utils.darkenStatusBar(this, R.color.colorToolbar);

//        if(loggedInUser.getAccounts().isEmpty()){
//            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
//        }

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_accounts);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PickEmailActivity.this, CreateAccountActivity.class);
                startActivity(i);

            }



        });


        LoginInterface loginService = RetrofitClient.getClient().create(LoginInterface.class);
        Call<ArrayList<User>> call = loginService.getUsers();



        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> users1 = response.body();



                SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                String json = prefs.getString("userObject", "");

                Gson gson = new Gson();
                final User loggedInUser = gson.fromJson(json, User.class);

                if(users1 == null){
                    Toast.makeText(PickEmailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }else {

//                            Toast.makeText(ContactsActivity.this, users1.get(0).getUsername() + users1.get(1).getUsername(), Toast.LENGTH_SHORT).show();

                    ArrayList<Account> tempAccounts = new ArrayList<>();

                    for(User user1 : users1){
                        if(user1.getId() == loggedInUser.getId()){
                            loggedInUser.getAccounts().clear();
                            for(Account acc : user1.getAccounts()){
                                tempAccounts.add(acc);
                            }

                        }
                    }




                    for(Account acc : tempAccounts){
                        loggedInUser.addAccount(acc);
                    }


                    adapter = new AccountAdapter(getApplicationContext(), loggedInUser.getAccounts());
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.e("pick position", String.valueOf(position));
////                Toast.makeText(PickEmailActivity.this, position, Toast.LENGTH_SHORT).show();
//
//                if(accounts.isEmpty()){
//                    Log.e("Empty check", "Empty in pick");
//                }

                            Account acc = loggedInUser.getAccounts().get(position);

                            Gson gson = new Gson();
                            String json = gson.toJson(acc);

                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("accObject", json);
                            editor.apply();
                            editor.commit();

                            Intent i = new Intent(PickEmailActivity.this, EmailsActivity.class);
                            startActivity(i);


                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(PickEmailActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        adapter = new AccountAdapter(getApplicationContext(), loggedInUser.getAccounts());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.e("pick position", String.valueOf(position));
////                Toast.makeText(PickEmailActivity.this, position, Toast.LENGTH_SHORT).show();
//
//                if(accounts.isEmpty()){
//                    Log.e("Empty check", "Empty in pick");
//                }

                Account acc = loggedInUser.getAccounts().get(position);

                Gson gson = new Gson();
                String json = gson.toJson(acc);

                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("accObject", json);
                editor.apply();
                editor.commit();

                Intent i = new Intent(PickEmailActivity.this, EmailsActivity.class);
                startActivity(i);


            }
        });
*/
    }
    @Override
    protected void onResume(){
        super.onResume();



    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

}
