package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.RetrofitClient;
import com.example.dopostemail.server.TagsInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTagActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setTitle("Create Tag");
        setContentView(R.layout.activity_create_tag);

        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = prefs.getString("userObject", "");
        Gson gson = new Gson();
        final User loggedInUser = gson.fromJson(json, User.class);


        Button btn = findViewById(R.id.btn_change_pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText tagName = findViewById(R.id.tagName);

                Tag tag = new Tag();
                tag.setMessage(new Message());
                tag.setName(tagName.getText().toString());
                tag.setUser(loggedInUser);


                TagsInterface tagsInterface = RetrofitClient.getClient().create(TagsInterface.class);

                Call<Tag> call = tagsInterface.saveTag(tag);

                call.enqueue(new Callback<Tag>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        Toast.makeText(CreateTagActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateTagActivity.this, TagsActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable t) {
                        Toast.makeText(CreateTagActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
