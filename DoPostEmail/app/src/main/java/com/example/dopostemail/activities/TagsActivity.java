package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.adapter.RuleAdapter;
import com.example.dopostemail.adapter.TagAdapter;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.RetrofitClient;
import com.example.dopostemail.server.TagsInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TagsActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    ListView mListView;
    TagAdapter adapter;
    ArrayList<Tag> userTag = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setTitle("Tags");
        setContentView(R.layout.activity_tags);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_contacts);
        setSupportActionBar(toolbar);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        mListView = findViewById(R.id.list_tags);

        drawer = findViewById(R.id.drawer_layout);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_tags);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TagsActivity.this, CreateTagActivity.class);
                startActivity(i);
            }
        });

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = prefs.getString("userObject", "");
        Gson gson = new Gson();
        final User loggedInUser = gson.fromJson(json, User.class);



        TagsInterface tagsInterface = RetrofitClient.getClient().create(TagsInterface.class);

        Call<ArrayList<Tag>> tags = tagsInterface.getTags();

        tags.enqueue(new Callback<ArrayList<Tag>>() {
            @Override
            public void onResponse(Call<ArrayList<Tag>> call, Response<ArrayList<Tag>> response) {
                ArrayList<Tag> userTags = response.body();

                for(Tag t1: userTags){
                    if(t1.getUser().getId() == loggedInUser.getId()){

                        userTag.add(t1);

                        adapter = new TagAdapter(getApplicationContext(), userTag);
                        mListView.setAdapter(adapter);

                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Tag tag = userTag.get(position);

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("tag", tag);

                                Intent i = new Intent(TagsActivity.this, TagActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);


                            }
                        });


                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Tag>> call, Throwable t) {
                Toast.makeText(TagsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
