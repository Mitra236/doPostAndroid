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

import android.widget.AdapterView;

import android.widget.ListView;


import com.example.dopostemail.R;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Condition;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Format;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Operation;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.model.Tag;

import java.util.ArrayList;
import java.util.List;


public class EmailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ListView mListView;
    private CustomAdapter adapter;
    private List<Message> messages;


    private ArrayList<Message> m = new ArrayList<>();
    private ArrayList<Message> m2 = new ArrayList<>();
    private ArrayList<Message> m3 = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();
    private ArrayList<Tag> tags2 = new ArrayList<>();
    private ArrayList<Tag> tags3 = new ArrayList<>();
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<Contact> to = new ArrayList<>();
    private ArrayList<Contact> to2 = new ArrayList<>();
    private ArrayList<Contact> to3 = new ArrayList<>();
    private ArrayList<Contact> cc = new ArrayList<>();
    private ArrayList<Contact> cc2 = new ArrayList<>();
    private ArrayList<Contact> bcc = new ArrayList<>();
    private ArrayList<Contact> bcc2 = new ArrayList<>();
    private ArrayList<Attachment> attachments = new ArrayList<>();
    private ArrayList<Attachment> attachments2 = new ArrayList<>();
    private ArrayList<Attachment> attachments3 = new ArrayList<>();

    public Contact conTemp = new Contact(1, "Pera", "Peric", "Pex", "pera123@gmail.com", Format.PLAIN);
    public Contact conTemp2 = new Contact(2, "Aleksandar", "Aleksic", "Acoo", "aco123@gmail.com", Format.HTML);
    public Contact conTemp3 = new Contact(3, "Maja", "Maric", "Maki", "maki123@gmail.com", Format.HTML);
    public Contact conTemp4 = new Contact(4, "me", "Stevic", "Stefi", "stefi123@gmail.com", Format.HTML);
    public Contact conTemp5 = new Contact(5, "Emily", "Emmy", "Emily", "emily123@gmail.com", Format.HTML);

    public Tag tagTemp = new Tag(1, "First Tag", m);
    public Tag tagTemp2 = new Tag(2, "Second Tag", m2);
    public Tag tagTemp3 = new Tag(3, "Third Tag", m3);
    public Tag tagTemp4 = new Tag(4, "Fourth Tag", m2);
    public Tag tagTemp5 = new Tag(5, "Fifth Tag", m3);


    private Rule rule = new Rule(1, Condition.TO, Operation.MOVE);
    private Rule rule2 = new Rule(2, Condition.FROM, Operation.DELETE);
    private Rule rule3 = new Rule(3, Condition.SUBJECT, Operation.COPY);

    public Folder folder = new Folder(1, "Drafts", new ArrayList<Folder>(),new ArrayList<Message>(),rule3);
    public Folder folder2 = new Folder(2, "Promotions", new ArrayList<Folder>(), new ArrayList<Message>(),rule);
    public Folder folder3 = new Folder(3, "Trash", new ArrayList<Folder>(), new ArrayList<Message>(),rule2);
    public Folder folder4 = new Folder(4, "Electronics", folders, new ArrayList<Message>(),rule2);
    public Folder folder5 = new Folder(5, "Recent promotions", folders, new ArrayList<Message>(),rule2);

    public Account account = new Account(1, "smtp", "pop3", "myemail@gmail.com", "123", m);


    public Message messageTemp = new Message(1, conTemp, to, cc, bcc,  "2019-02-13 09:50", "Matematika 1" , "This is some message", tags, attachments, folder, account );
    public Message messageTemp2 = new Message(2, conTemp2, to2, new ArrayList<Contact>(), bcc2, "2019-01-29 13:24",  "Osnove programiranja", "Just a dumb message",tags2, attachments2, folder2, account);
    public Message messageTemp3 = new Message(3,  conTemp3, to3, cc2, new ArrayList<Contact>(),"2019-03-19 22:22", "Sistemski softver", "Another dumb message", tags3, attachments3, folder3, account);

    public Attachment attachment = new Attachment(1, "some data", "type1", "attachment1", messageTemp );
    public Attachment attachment2 = new Attachment(2, "some data", "type2", "attachment2", messageTemp2 );
    public Attachment attachment3 = new Attachment(3, "some data", "type3", "attachment3", messageTemp3 );
    public Attachment attachment4 = new Attachment(4, "some data", "type4", "attachment4", messageTemp );
    public Attachment attachment5 = new Attachment(5, "some data", "type5", "attachment5", messageTemp3 );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Emails");
        setContentView(R.layout.activity_posts);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_emails);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);

        m.add(messageTemp);
        m2.add(messageTemp2);
        m3.add(messageTemp3);

        tags.add(tagTemp);
        tags2.add(tagTemp2);
        tags2.add(tagTemp4);
        tags3.add(tagTemp3);
        tags3.add(tagTemp5);

        to.add(conTemp4);
        to2.add(conTemp4);
        to2.add(conTemp5);
        to3.add(conTemp4);
        cc.add(conTemp3);
        cc.add(conTemp5);
        bcc.add(conTemp5);
        cc2.add(conTemp);
        cc2.add(conTemp4);
        bcc2.add(conTemp);
        bcc2.add(conTemp2);

        folders.add(folder4);
        folders.add(folder5);

        attachments.add(attachment);
        attachments.add(attachment4);
        attachments2.add(attachment2);
        attachments3.add(attachment3);
        attachments3.add(attachment5);



        messages = new ArrayList<>();

        messages.add(messageTemp);
        messages.add(messageTemp2);
        messages.add(messageTemp3);

        adapter = new CustomAdapter(getApplicationContext(), messages);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Message m = messages.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("messages", m);

                Intent i = new Intent(EmailsActivity.this, EmailActivity.class);
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
                drawer.closeDrawer(Gravity.START, true);
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
            case R.id.nav_logout:
                Intent m = new Intent(EmailsActivity.this, LoginActivity.class);
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

    public ArrayList<Folder> getFolders() {
        return folders;
    }
}
