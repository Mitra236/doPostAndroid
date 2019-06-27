package com.example.dopostemail.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    ListView mListView;
    private ContactsAdapter adapter;
    private List<Contact> contacts;
//    private List<User> users;
    private long mInterval = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Contacts");
        setContentView(R.layout.activity_contacts);
        mHandler = new Handler();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = pref.getString("userObject", "");

        Gson gson = new Gson();
        final Account acc = gson.fromJson(json, Account.class);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_contacts);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", acc);
                intent.removeExtra("user");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_contacts);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsActivity.this, CreateContactActivity.class);
                startActivity(i);
            }
        });
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_emails:
                Intent i = new Intent(ContactsActivity.this, EmailsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_contacts:
                drawer.closeDrawer(Gravity.START, true);
                break;
            case R.id.nav_folders:
                Intent k = new Intent(ContactsActivity.this, FoldersActivity.class);
                startActivity(k);
                break;
            case R.id.nav_settings:
                Intent l = new Intent(ContactsActivity.this, SettingsActivity.class);
                startActivity(l);
                break;
            case R.id.nav_tags:
                Intent r = new Intent(ContactsActivity.this, TagsActivity.class);
                startActivity(r);
                break;
            case R.id.nav_logout:
                Intent m = new Intent(ContactsActivity.this, LoginActivity.class);
                SharedPreferences settings = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                settings.edit().remove("currentUser").commit();
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(m);
                break;
        }

        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
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



//                Log.e("User", loggedInUser.getUsername());

//                Toast.makeText(ContactsActivity.this, loggedInUser.getContacts().get(0).getDisplay(), Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(ContactsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }else {

//                            Toast.makeText(ContactsActivity.this, users1.get(0).getUsername() + users1.get(1).getUsername(), Toast.LENGTH_SHORT).show();

                            ArrayList<Contact> contactsTemp = new ArrayList<>();

                            for(User user1 : users1){

                                if(user1.getId() == loggedInUser.getId()){
                                    loggedInUser.getContacts().clear();
                                    for(Contact con : user1.getContacts()){
                                        contactsTemp.add(con);
                                    }

                                }
                            }




                            for(Contact con : contactsTemp){
                                loggedInUser.addContact(con);
                            }


                            adapter = new ContactsAdapter(getApplicationContext(), loggedInUser.getContacts());
                            mListView.setAdapter(adapter);

                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Contact con = loggedInUser.getContacts().get(position);

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("contacts", con);

                                    Intent i = new Intent(ContactsActivity.this, ContactActivity.class);
                                    i.putExtras(bundle);
                                    startActivity(i);


                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Toast.makeText(ContactsActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
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
