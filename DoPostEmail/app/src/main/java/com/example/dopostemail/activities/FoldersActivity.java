package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.adapter.FoldersAdapter;
import com.example.dopostemail.model.Condition;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Operation;
import com.example.dopostemail.model.Rule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FoldersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ListView mListView;
    private FoldersAdapter adapter;
    private List<Folder> folders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        Rule rule = new Rule(1, Condition.TO, Operation.MOVE);
        Rule rule2 = new Rule(2, Condition.FROM, Operation.DELETE);
        Rule rule3 = new Rule(3, Condition.SUBJECT, Operation.COPY);

        Folder folder = new Folder(1, "Drafts", new ArrayList<Folder>(), rule3);
        Folder folder2 = new Folder(2, "Promotions", new ArrayList<Folder>(), rule);
        Folder folder3 = new Folder(3, "Trash", new ArrayList<Folder>(), rule2);
        Folder folder4 = new Folder(4, "Electronics", new ArrayList<Folder>(), rule2);
        Folder folder5 = new Folder(5, "Recent promotions", new ArrayList<Folder>(), rule2);
u
        folders = new ArrayList<>();
        folders.add(folder);
        folders.add(folder2);
        folders.add(folder3);
        folders.add(folder4);
        folders.add(folder5);

        adapter = new FoldersAdapter(getApplicationContext(), folders);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Folder f = folders.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("folders", f);

                Intent i = new Intent(FoldersActivity.this, FolderActivity.class);
                i.putExtras(bundle);
                startActivity(i);


            }
        });

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
