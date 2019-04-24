package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.FoldersAdapter;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Dummy;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;

import java.util.ArrayList;

public class FoldersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ListView mListView;
    private FoldersAdapter adapter;
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Contact> contact1 = new ArrayList<>();
    private ArrayList<Folder> folderss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        /*Rule rule = new Rule(1, Condition.TO, Operation.MOVE);
        Rule rule2 = new Rule(2, Condition.FROM, Operation.DELETE);
        Rule rule3 = new Rule(3, Condition.SUBJECT, Operation.COPY);


        Folder folder = new Folder(1, "Drafts", new ArrayList<Folder>(), rule3);
        Folder folder2 = new Folder(2, "Promotions", new ArrayList<Folder>(), rule);
        Folder folder3 = new Folder(3, "Trash", new ArrayList<Folder>(), rule2);
        Folder folder4 = new Folder(4, "Electronics", new ArrayList<Folder>(), rule2);
        Folder folder5 = new Folder(5, "Recent promotions", new ArrayList<Folder>(), rule2);

        folders = new ArrayList<>();
        folders.add(folder);
        folders.add(folder2);
        folders.add(folder3);
        folders.add(folder4);
        folders.add(folder5);

        Folder folder = new Folder(1, "Drafts", new ArrayList<Folder>(), messages, rule3);
        Folder folder2 = new Folder(2, "Promotions", new ArrayList<Folder>(), messages, rule);
        Folder folder3 = new Folder(3, "Trash", new ArrayList<Folder>(), messages, rule2);
        Folder folder4 = new Folder(4, "Electronics", folders, messages, rule2);
        Folder folder5 = new Folder(5, "Recent promotions", folders, messages, rule2);

        Contact con1 = new Contact("John");
        Message mess1 = new Message(con1, "You passed!Congrats!");


        messages.add(mess1);

        folders.add(folder4);
        folders.add(folder5);

        folderss = new ArrayList<>();
        folderss.add(folder);
        folderss.add(folder2);
        folderss.add(folder3);

        folders.add(folder4);
        folders.add(folder5);

        folder2.getFolders().addAll(folders)*/

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("folders", f);

        ListView mListView = findViewById(R.id.list_view);
        ArrayList<Folder> folders = new Dummy().getFolders();
        FoldersAdapter fa = new FoldersAdapter(this, R.layout.activity_listview,folders);
        mListView.setAdapter(fa);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoldersActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });

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
