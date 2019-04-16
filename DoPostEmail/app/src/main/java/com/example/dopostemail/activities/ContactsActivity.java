package com.example.dopostemail.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.dopostemail.model.Contact;

public class ContactsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    ListView mListView;

    int[] images  = {R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon,
            R.drawable.contacts_icon};

    String[] names = {"Alexander",
            "John",
            "Emily",
            "Oliver",
            "Thomas",
            "Charlie",
            "Phillip",
            "Amelia",
            "Margaret",
            "Batman",
            "Bessy",
            "Minnie Mouse"};

    String[] emails = {"alex@gmail.com",
            "john@gmail.com",
            "emma12@gmail.com",
            "oli998@gmail.com",
            "thomas123@gmail.com",
            "charlie111@gmail.com",
            "phillip123@gmail.com",
            "amelia.s@gmail.com",
            "margaretwhite@gmail.com",
            "batman11@gmail.com",
            "bessy77@gmail.com",
            "minniemouse@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Contacts");
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_contacts);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ContactsActivity.this, ContactActivity.class);
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
                Intent i = new Intent(ContactsActivity.this, ProfileActivity.class);
                startActivity(i);

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
            mSubTitle.setText(emails[position]);
            return view;
        }
    }

}
