package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.gson.Gson;

import java.util.List;

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
