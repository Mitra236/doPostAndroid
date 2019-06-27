package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Condition;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Operation;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFolderActivity extends AppCompatActivity {

    private EditText folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Folder");
        setContentView(R.layout.activity_create_folder);

        Toolbar toolbar = findViewById(R.id.toolbar_create_folder);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateFolderActivity.this, FoldersActivity.class);
                startActivity(i);
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        folderName = findViewById(R.id.folder_name);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = prefs.getString("accObject", "");

        Gson gson = new Gson();
        final Account acc = gson.fromJson(json, Account.class);

        Button btnCreateRule = findViewById(R.id.addRules);

        btnCreateRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateFolderActivity.this, RulesActivity.class));
            }
        });

        Button btnCreate = findViewById(R.id.button_save_cf);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                Folder folder = new Folder(folderName.getText().toString(), new Folder(), new ArrayList<Message>(), new ArrayList<Rule>());
                folder.setAccount(acc);
                Call<Folder> call = service.saveFolder(folder);

                call.enqueue(new Callback<Folder>() {
                    @Override
                    public void onResponse(Call<Folder> call, Response<Folder> response) {
                        Toast.makeText(CreateFolderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Folder> call, Throwable t) {
                        Toast.makeText(CreateFolderActivity.this, "failure", Toast.LENGTH_SHORT).show();
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

        Button cancel = (Button)findViewById(R.id.button_cancel_cf);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateFolderActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

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
