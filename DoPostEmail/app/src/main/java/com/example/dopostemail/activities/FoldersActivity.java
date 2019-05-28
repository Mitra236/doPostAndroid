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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;

import java.util.ArrayList;

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

//    private ArrayList<Message> m = new ArrayList<>();
//    private ArrayList<Message> m2 = new ArrayList<>();
//    private ArrayList<Message> m3 = new ArrayList<>();
//    private ArrayList<Tag> tags = new ArrayList<>();
//    private ArrayList<Tag> tags2 = new ArrayList<>();
//    private ArrayList<Tag> tags3 = new ArrayList<>();
//    private ArrayList<Folder> folders1 = new ArrayList<>();
//    private ArrayList<Contact> to = new ArrayList<>();
//    private ArrayList<Contact> to2 = new ArrayList<>();
//    private ArrayList<Contact> to3 = new ArrayList<>();
//    private ArrayList<Contact> cc = new ArrayList<>();
//    private ArrayList<Contact> cc2 = new ArrayList<>();
//    private ArrayList<Contact> bcc = new ArrayList<>();
//    private ArrayList<Contact> bcc2 = new ArrayList<>();
//    private ArrayList<Attachment> attachments = new ArrayList<>();
//    private ArrayList<Attachment> attachments2 = new ArrayList<>();
//    private ArrayList<Attachment> attachments3 = new ArrayList<>();
//    private ArrayList<Message> mes1 = new ArrayList<>();
//    private ArrayList<Message> mes2 = new ArrayList<>();
//    private ArrayList<Message> mes3 = new ArrayList<>();
//
//    public Contact conTemp = new Contact(1, "Pera", "Peric", "Pex", "pera123@gmail.com", Format.PLAIN);
//    public Contact conTemp2 = new Contact(2, "Aleksandar", "Aleksic", "Acoo", "aco123@gmail.com", Format.HTML);
//    public Contact conTemp3 = new Contact(3, "Maja", "Maric", "Maki", "maki123@gmail.com", Format.HTML);
//    public Contact conTemp4 = new Contact(4, "me", "Stevic", "Stefi", "stefi123@gmail.com", Format.HTML);
//    public Contact conTemp5 = new Contact(5, "Emily", "Emmy", "Emily", "emily123@gmail.com", Format.HTML);
//
//    public Tag tagTemp = new Tag(1, "First Tag", m);
//    public Tag tagTemp2 = new Tag(2, "Second Tag", m2);
//    public Tag tagTemp3 = new Tag(3, "Third Tag", m3);
//    public Tag tagTemp4 = new Tag(4, "Fourth Tag", m2);
//    public Tag tagTemp5 = new Tag(5, "Fifth Tag", m3);
//
//
//    private Rule rule = new Rule(1, Condition.TO, Operation.MOVE);
//    private Rule rule2 = new Rule(2, Condition.FROM, Operation.DELETE);
//    private Rule rule3 = new Rule(3, Condition.SUBJECT, Operation.COPY);
//
//    public Folder folder = new Folder(1, "Drafts", new ArrayList<Folder>(),mes1,rule3);
//    public Folder folder2 = new Folder(2, "Promotions", folders1, mes2,rule);
//    public Folder folder3 = new Folder(3, "Trash", new ArrayList<Folder>(), mes3,rule2);
//    public Folder folder4 = new Folder(4, "Electronics", new ArrayList<Folder>(), new ArrayList<Message>(),rule2);
//    public Folder folder5 = new Folder(5, "Recent promotions", new ArrayList<Folder>(), new ArrayList<Message>(),rule2);
//
//    public Account account = new Account(1, "smtp", "pop3", "myemail@gmail.com", "123", m);
//
//
//    public Message messageTemp = new Message(1, conTemp, to, cc, bcc,  "2019-02-13 09:50", "Matematika 1" , "This is some message", tags, attachments, folder, account );
//    public Message messageTemp2 = new Message(2, conTemp2, to2, new ArrayList<Contact>(), bcc2, "2019-01-29 13:24",  "Osnove programiranja", "Just a dumb message",tags2, attachments2, folder2, account);
//    public Message messageTemp3 = new Message(3,  conTemp3, to3, cc2, new ArrayList<Contact>(),"2019-03-19 22:22", "Sistemski softver", "Another dumb message", tags3, attachments3, folder3, account);
//
//    public Attachment attachment = new Attachment(1, "some data", "type1", "attachment1", messageTemp );
//    public Attachment attachment2 = new Attachment(2, "some data", "type2", "attachment2", messageTemp2 );
//    public Attachment attachment3 = new Attachment(3, "some data", "type3", "attachment3", messageTemp3 );
//    public Attachment attachment4 = new Attachment(4, "some data", "type4", "attachment4", messageTemp );
//    public Attachment attachment5 = new Attachment(5, "some data", "type5", "attachment5", messageTemp3 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setTitle("Folders");
        setContentView(R.layout.activity_folders);

        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);


//        m.add(messageTemp);
//        m2.add(messageTemp2);
//        m3.add(messageTemp3);
//
//        tags.add(tagTemp);
//        tags2.add(tagTemp2);
//        tags2.add(tagTemp4);
//        tags3.add(tagTemp3);
//        tags3.add(tagTemp5);
//
//        to.add(conTemp4);
//        to2.add(conTemp4);
//        to2.add(conTemp5);
//        to3.add(conTemp4);
//        cc.add(conTemp3);
//        cc.add(conTemp5);
//        bcc.add(conTemp5);
//        cc2.add(conTemp);
//        cc2.add(conTemp4);
//        bcc2.add(conTemp);
//        bcc2.add(conTemp2);
//
//        folders1.add(folder4);
//        folders1.add(folder5);
//
//        attachments.add(attachment);
//        attachments.add(attachment4);
//        attachments2.add(attachment2);
//        attachments3.add(attachment3);
//        attachments3.add(attachment5);
//
//        mes1.add(messageTemp);
//        mes2.add(messageTemp2);
//        mes3.add(messageTemp3);




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

//        ListView mListView = findViewById(R.id.list_view);
//        ArrayList<Folder> folders = new Dummy().getFolders();
//        FoldersAdapter fa = new FoldersAdapter(this, R.layout.activity_listview,folders);
//        mListView.setAdapter(fa);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoldersActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });

        FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
        Call<ArrayList<Folder>> call = service.getFolders();

        showProgress();

        call.enqueue(new Callback<ArrayList<Folder>>() {
            @Override
            public void onResponse(Call<ArrayList<Folder>> call, Response<ArrayList<Folder>> response) {
                ArrayList<Folder> folders1 = response.body();

                if(folders1 == null){
                    Toast.makeText(FoldersActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }else {
                    folders = folders1;

                    adapter = new FolderAdapter(getApplicationContext(), folders);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Folder fol = folders.get(position);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("folder", fol);

                            Intent i = new Intent(FoldersActivity.this, FolderActivity.class);
                            i.putExtras(bundle);
                            startActivity(i);


                        }
                    });
                }
            }



            @Override
            public void onFailure(Call<ArrayList<Folder>> call, Throwable t) {
                Toast.makeText(FoldersActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
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
//        startRepeatingTask();
    }

//    Runnable mStatusChecker = new Runnable() {
//        @Override
//        public void run() {
//            try {
//
//                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                String syncTimeStr = pref.getString("refresh_rate", "0");
//                mInterval = TimeUnit.MINUTES.toMillis(Integer.parseInt(syncTimeStr));
//
//                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
//                Call<ArrayList<Folder>> call = service.getFolders();
//
//                call.enqueue(new Callback<ArrayList<Folder>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Folder>> call, Response<ArrayList<Folder>> response) {
//                        ArrayList<Folder> folders1 = response.body();
//
//                        if (folders1 == null) {
//                            Toast.makeText(FoldersActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        } else {
//                            folders = folders1;
//
//                            adapter = new FolderAdapter(getApplicationContext(), folders);
//                            mListView.setAdapter(adapter);
//
//                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                    Folder f = folders.get(position);
//
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("folder", f);
//
//                                    Intent i = new Intent(FoldersActivity.this, FolderActivity.class);
//                                    i.putExtras(bundle);
//                                    startActivity(i);
//
//
//                                }
//                            });
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Folder>> call, Throwable t) {
//
//                    }
//                });
//
//
//            } finally {
//                mHandler.postDelayed(mStatusChecker, mInterval);
//            }
//        }
//    };

//    void startRepeatingTask() {
//        mStatusChecker.run();
//    }

//    void stopRepeatingTask() {
//        mHandler.removeCallbacks(mStatusChecker);
//    }
    @Override
    protected void onPause(){
        super.onPause();
//        stopRepeatingTask();
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
