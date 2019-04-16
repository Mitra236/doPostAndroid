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

public class FoldersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    ListView mListView;

    int[] images  = {R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon,
            R.drawable.foldericon};

    String[] names = {"Folder 1",
            "Folder 2",
            "Folder 3",
            "Folder 4",
            "Folder 5",
            "Folder 6",
            "Folder 7",
            "Folder 8",
            "Folder 9",
            "Folder 10",
            "Folder 11",
            "Folder 12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(FoldersActivity.this, FolderActivity.class);
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

    class CustomAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = getLayoutInflater().inflate(R.layout.activity_listview, null);

            ImageView mImageView = view.findViewById(R.id.icon);
            TextView mTitle = view.findViewById(R.id.title);

            mImageView.setImageResource(images[position]);
            mTitle.setText(names[position]);
            return view;
        }
    }
}
