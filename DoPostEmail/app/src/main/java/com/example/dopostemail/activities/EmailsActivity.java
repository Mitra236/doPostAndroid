package com.example.dopostemail.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dopostemail.R;

public class EmailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    ListView mListView;

    int[] images  = {R.drawable.lettera,
            R.drawable.letterj,
            R.drawable.lettere,
            R.drawable.letterm,
            R.drawable.letters,
            R.drawable.lettera,
            R.drawable.letterp,
            R.drawable.lettere,
            R.drawable.letterd,
            R.drawable.letterb,
            R.drawable.letterb,
            R.drawable.letterm};

    String[] names = {"Alexander",
                    "John",
                    "Emily",
                    "Mickey",
                    "Stephen",
                    "Anna",
                    "Phillip",
                    "Eva",
                    "Dean",
                    "Batman",
                    "Bessy",
                    "Minnie Mouse"};

    String[] dates = {"13 March",
                    "17 March",
                    "12 February",
                    "18 January",
                    "1 April",
                    "28 March",
                    "23 February",
                    "16 January",
                    "5 April",
                    "1 May",
                    "3 June",
                    "30 April"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Emails");
        setContentView(R.layout.activity_posts);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_emails);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(EmailsActivity.this, EmailActivity.class);
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
                Intent i = new Intent(EmailsActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_emails);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, CreateEmailActivity.class);
                startActivity(i);

            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_emails:
                Intent i = new Intent(EmailsActivity.this, EmailsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_contacts:
                Intent j = new Intent(EmailsActivity.this, ContactsActivity.class);
                startActivity(j);
                break;
            case R.id.nav_folders:
                Intent k = new Intent(EmailsActivity.this, FoldersActivity.class);
                startActivity(k);
                break;
            case R.id.nav_settings:
                Intent l = new Intent(EmailsActivity.this, SettingsActivity.class);
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
            TextView mSubTitle = view.findViewById(R.id.subTitle);

            mImageView.setImageResource(images[position]);
            mTitle.setText(names[position]);
            mSubTitle.setText(dates[position]);
            return view;
        }
    }
}
