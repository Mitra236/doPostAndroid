package com.example.dopostemail.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    ListView mListView;
    private ContactsAdapter adapter;
    private List<Contact> contacts;





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Contacts");
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_contacts);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

//        Photo photo1 = new Photo(1, R.drawable.pikachu);
//        Photo photo2 = new Photo(1, R.drawable.contacts_icon);

//        Message message1 = new Message(1, "Matematika 1", "This is some message");
//        Message message2 = new Message(1, "Osnovne programiranja", "This is another message");
//        Message message3 = new Message(1, "Sistemski softver", "This is (you guessed it) some message");

//        EmailsActivity em = new EmailsActivity();

//        Message message1 = em.messageTemp;
//        Message message2 = em.messageTemp2;
//        Message message3 = em.messageTemp3;

//        ArrayList<Message> to1 = new ArrayList<>();
//        ArrayList<Message> from1 = new ArrayList<>();
//        ArrayList<Message> cc1 = new ArrayList<>();
//        ArrayList<Message> bcc1 = new ArrayList<>();
//        ArrayList<Message> to2 = new ArrayList<>();
//        ArrayList<Message> from2 = new ArrayList<>();
//        ArrayList<Message> cc2 = new ArrayList<>();
//        ArrayList<Message> bcc2 = new ArrayList<>();

//        to1.add(message1);
//        to2.add(message2);
//        from1.add(message2);
//        from2.add(message1);
//        cc1.add(message1);
//        cc1.add(message2);
//        bcc1.add(message2);
//        bcc1.add(message1);
//        cc2.add(message3);
//        cc2.add(message2);
//        bcc2.add(message1);
//        bcc2.add(message2);
//        bcc2.add(message3);

//        Contact conTemp = new Contact(1, "Pera", "Peric", "Pex", "pera123@gmail.com", Format.PLAIN, photo1, to1, from1, cc1, bcc1);
//        Contact conTemp2 = new Contact(2, "Aleksandar", "Aleksic", "Acoo", "aco123@gmail.com", Format.HTML, photo2, to1, from2, cc1, bcc2);
//        Contact conTemp3 = new Contact(3, "Maja", "Maric", "Maki", "maki123@gmail.com", Format.HTML, photo2, to2, from1, cc2, bcc1);
//        Contact conTemp4 = new Contact(4, "me", "Stevic", "Stefi", "stefi123@gmail.com", Format.HTML, photo2, to1, from1, cc2, bcc2);
//        Contact conTemp5 = new Contact(5, "Emily", "Emmy", "Emily", "emily123@gmail.com", Format.HTML, photo2, to2, from2, cc1, bcc2);


        ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
        Call<ArrayList<Contact>> call = service.getContacts();

        showProgress();

        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                ArrayList<Contact> contacts1 = response.body();

                if(contacts1 == null){
                    Toast.makeText(ContactsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }else {
                    contacts = contacts1;

                    adapter = new ContactsAdapter(getApplicationContext(), contacts);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Contact con = contacts.get(position);

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
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                Toast.makeText(ContactsActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
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

//    class CustomAdapter extends BaseAdapter {
//
//
//
//        @Override
//        public int getCount() {
//            return images.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//
//            View view = getLayoutInflater().inflate(R.layout.activity_listview, null);
//
//            ImageView mImageView = view.findViewById(R.id.icon);
//            TextView mTitle = view.findViewById(R.id.title);
//            TextView mSubTitle = view.findViewById(R.id.subTitle);
//
//            mImageView.setImageResource(images[position]);
//            mTitle.setText(names[position]);
//            mSubTitle.setText(emails[position]);
//            return view;
//        }
//    }

}
