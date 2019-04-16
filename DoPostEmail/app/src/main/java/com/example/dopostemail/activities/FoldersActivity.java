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
import android.widget.Toast;

import com.example.dopostemail.R;

public class FoldersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

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
