package com.example.dopostemail.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.example.dopostemail.server.TagsInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setTitle("Tag");
        setContentView(R.layout.activity_tag);

        Toolbar toolbar = findViewById(R.id.toolbar_tag);
        setSupportActionBar(toolbar);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TagActivity.this, TagsActivity.class);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        final Tag t = (Tag) bundle.getSerializable("tag");

        final EditText tbTagName = findViewById(R.id.tagName);
        tbTagName.setText(t.getName());

        Button btnDelete = findViewById(R.id.button_delete_tag);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);

                builder.setTitle("Confirm");

                builder.setMessage("Are you sure that you want to delete this tag?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TagsInterface service = RetrofitClient.getClient().create(TagsInterface.class);
                        Call<Void> call = service.deleteTag(t.getId());

                        Log.e("Id", String.valueOf(t.getId()));

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(TagActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(TagActivity.this, TagsActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(TagActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button btnEdit = findViewById(R.id.button_change_tag);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                String json = prefs.getString("userObject", "");
                Gson gson = new Gson();
                final User loggedInUser = gson.fromJson(json, User.class);


                TagsInterface service = RetrofitClient.getClient().create(TagsInterface.class);
                Tag tag = new Tag(t.getId(), tbTagName.getText().toString(), t.getMessage(), loggedInUser);
                Call<Tag> call = service.updateTag(tag, t.getId());


                call.enqueue(new Callback<Tag>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        Toast.makeText(TagActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(TagActivity.this, TagsActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable t) {
                        Toast.makeText(TagActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
