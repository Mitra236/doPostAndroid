package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickFolderActivity extends AppCompatActivity {

    private ListView mListView;
    private FolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folders");
        setContentView(R.layout.activity_pick_folder);

        Toolbar toolbar = findViewById(R.id.toolbar_pick_folder);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view_pick_folder);
        Utils.darkenStatusBar(this, R.color.colorToolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json2 = pref.getString("accObject", "");
        Gson gson = new Gson();
        final Account loggedInAcc = gson.fromJson(json2, Account.class);

        adapter = new FolderAdapter(getApplicationContext(), loggedInAcc.getFolders());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Folder fol = loggedInAcc.getFolders().get(position);

                Bundle bundle = getIntent().getExtras();
                final Message m = (Message) bundle.getSerializable("msg");

                Log.e("MSG TEST" , m.getSubject() + " " + m.getId());

//                m.setFolder(fol);
//                fol.addMessage(m);

                Log.e("Test" , "1");

                MessagesInterface service1 = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<Message> call = service1.setFolderMessage(m, loggedInAcc.getId(), fol.getId());

                Log.e("Test" , m.getId().toString());

                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Toast.makeText(PickFolderActivity.this, "Responded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(PickFolderActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });
                Log.e("Test" , "2");
                Intent i = new Intent(PickFolderActivity.this, FoldersActivity.class);
                startActivity(i);


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
