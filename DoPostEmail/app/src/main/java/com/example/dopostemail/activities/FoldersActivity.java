package com.example.dopostemail.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoldersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ListView mListView;
    private FolderAdapter adapter;
    private ArrayList<Folder> folders;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Contact> contact1 = new ArrayList<>();
    private ArrayList<Folder> folderss;
    private long mInterval = 0;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = pref.getString("userObject", "");

        Gson gson = new Gson();
        final Account acc = gson.fromJson(json, Account.class);


        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoldersActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", acc);
                intent.removeExtra("user");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
/*

*/
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_folders);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoldersActivity.this, CreateFolderActivity.class);
                startActivity(i);

            }



        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_emails:
                Intent i = new Intent(FoldersActivity.this, EmailsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_contacts:
                Intent j = new Intent(FoldersActivity.this, ContactsActivity.class);
                startActivity(j);
                break;
            case R.id.nav_folders:
                drawer.closeDrawer(Gravity.START, true);
                break;
            case R.id.nav_settings:
                Intent l = new Intent(FoldersActivity.this, SettingsActivity.class);
                startActivity(l);
                break;
            case R.id.nav_logout:
                Intent m = new Intent(FoldersActivity.this, LoginActivity.class);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SharedPreferences settings = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                settings.edit().remove("currentUser").commit();
                startActivity(m);
                break;
        }

        return true;
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    public void showProgress() {
        final int THREE_SECONDS = 2*1000;
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setMessage("Loading...");
        dlg.setCancelable(false);
        dlg.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dlg.dismiss();
            }
        }, THREE_SECONDS);
    }

    @Override
    protected void onResume(){
        super.onResume();
        startRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0);
                String syncTimeStr = pref.getString("refresh_rate", "1");
                String[] split = syncTimeStr.split(" ");
                String syncTime = split[0];
                mInterval = TimeUnit.MINUTES.toMillis(Integer.parseInt(syncTime));
//                Toast.makeText(FoldersActivity.this, Long.toString(mInterval), Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                String json = prefs.getString("accObject", "");

                Gson gson = new Gson();
                final Account acc = gson.fromJson(json, Account.class);



                adapter = new FolderAdapter(getApplicationContext(), acc.getFolders());
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Folder fol = acc.getFolders().get(position);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("folder", fol);

                        Intent i = new Intent(FoldersActivity.this, FolderActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);


                    }
                });








            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
    @Override
    protected void onPause(){
        super.onPause();
        stopRepeatingTask();
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
