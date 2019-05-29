package com.example.dopostemail.activities;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dopostemail.R;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ListView mListView;
    private CustomAdapter adapter;
    private List<Message> messages;

    private final String CHANNEL_ID = "my_channel";
    private final int NOTIFICATION_ID = 001;
    private int counter = 0;
    private long mInterval = 0;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Emails");
        setContentView(R.layout.activity_posts);
        mHandler = new Handler();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
        String json = pref.getString("userObject", "");

        Gson gson = new Gson();
        final Account acc = gson.fromJson(json, Account.class);

//        TextView tv = (TextView)findViewById(R.id.navUsername);
//        tv.setText("asd");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView2 = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView2.findViewById(R.id.navUsername);
        navUsername.setText(acc.getUsername());


//        Toast.makeText(this, acc.getUsername(), Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.nav_toolbar_emails);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_view);
//        mListView.setTextFilterEnabled(true);



        startRepeatingTask();


        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View headerView = navView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailsActivity.this, ProfileActivity.class);
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

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_emails);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, CreateEmailActivity.class);
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
                SharedPreferences settings = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                settings.edit().remove("currentUser").apply();
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(m);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_emails_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_view:

                SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                final android.support.v7.widget.SearchView searchView = (SearchView) item.getActionView();
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);

                        return true;
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", 0);
                String syncTimeStr = pref.getString("refresh_rate", "1");
                String[] split = syncTimeStr.split(" ");
                String syncTime = split[0];
//                String syncTimeStr = "2";
                mInterval = TimeUnit.MINUTES.toMillis(Integer.parseInt(syncTime));

//                Toast.makeText(EmailsActivity.this, Long.toString(mInterval), Toast.LENGTH_SHORT).show();

                MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<ArrayList<Message>> call = service.getMessages();
//        showProgress();

                call.enqueue(new Callback<ArrayList<Message>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                        ArrayList<Message> messages1 = response.body();


                        if(messages1 == null){
                            Toast.makeText(EmailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }else {

                            messages = messages1;


                            final NotificationCompat.Builder builder = new NotificationCompat.Builder(EmailsActivity.this, CHANNEL_ID);
                            for(Message m1 : messages){
                                if(!m1.isMessageRead()){
                                    counter++;
                                    if(counter <= 1) {
                                        Intent intent = new Intent(EmailsActivity.this, EmailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("messages", m1);
                                        intent.removeExtra("messages");
                                        intent.putExtras(bundle);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        final PendingIntent intentPending = PendingIntent.getActivity(EmailsActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//                                long futureInMillis = SystemClock.elapsedRealtime() + 1000;
//                                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, intentPending);

                                        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        Calendar calendar1Notify = Calendar.getInstance();
                                        calendar1Notify.setTimeInMillis(System.currentTimeMillis());
                                        calendar1Notify.set(Calendar.HOUR_OF_DAY, 8);
                                        calendar1Notify.set(Calendar.MINUTE, 00);

                                        alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1Notify.getTimeInMillis(), intentPending);

                                        long time24h = 24*60*60*1000;

                                        alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar1Notify.getTimeInMillis(),time24h,intentPending);


                                        builder.setSmallIcon(R.drawable.ic_sms_notification);
                                        if(m1.getId() == 1){
                                            builder.setContentTitle(m1.getFrom().getFirstName() + " " + m1.getFrom().getLastName() + "     " + counter);
                                        }else if(m1.getId() == 2){
                                            builder.setContentTitle(m1.getFrom().getEmail()+  "     " + counter);
                                        }else {
                                            builder.setContentTitle(m1.getFrom().getFirstName() + " " + m1.getFrom().getLastName() + "     " + counter);
                                        }

                                        builder.setContentText(m1.getContent());
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                        builder.setContentIntent(intentPending);
                                        builder.setAutoCancel(true);
                                        builder.build().flags |= Notification.FLAG_AUTO_CANCEL;


                                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(EmailsActivity.this);
                                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                                    }else {

                                        Intent intent = new Intent(EmailsActivity.this, EmailsActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        final PendingIntent intentPending = PendingIntent.getActivity(EmailsActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        Calendar calendar1Notify = Calendar.getInstance();
                                        calendar1Notify.setTimeInMillis(System.currentTimeMillis());
                                        calendar1Notify.set(Calendar.HOUR_OF_DAY, 8);
                                        calendar1Notify.set(Calendar.MINUTE, 00);

                                        alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1Notify.getTimeInMillis(), intentPending);

                                        long time24h = 24*60*60*1000;

                                        alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar1Notify.getTimeInMillis(),time24h,intentPending);



                                        builder.setSmallIcon(R.drawable.ic_sms_notification);
                                        if(m1.getId() == 1){
                                            builder.setContentTitle(m1.getFrom().getFirstName() + " " + m1.getFrom().getLastName() + "     " + counter);
                                        }else if(m1.getId() == 2){
                                            builder.setContentTitle(m1.getFrom().getEmail()+  "     " + counter);
                                        }else {
                                            builder.setContentTitle(m1.getFrom().getFirstName() + " " + m1.getFrom().getLastName() + "     " + counter);
                                        }
                                        builder.setContentText(m1.getContent());
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                        builder.setContentIntent(intentPending);
                                        builder.setAutoCancel(true);
                                        builder.build().flags |= Notification.FLAG_AUTO_CANCEL;


                                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(EmailsActivity.this);
                                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

                                    }


                                }
                            }

                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                CharSequence name = "Message notification";
                                String description = "Include all message notifications";

                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

                                notificationChannel.setDescription(description);

                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.createNotificationChannel(notificationChannel);
                            }



                            adapter = new CustomAdapter(getApplicationContext(), messages);
                            mListView.setAdapter(adapter);



                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    final Message m = messages.get(position);

                                    if (!m.isMessageRead()) {
                                        MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);

                                        Message mess = new Message(m.getId(), m.getFrom(), m.getTo(), m.getCc(), m.getBcc(), m.getDateTime(), m.getSubject(), m.getContent(), m.getTag(), m.getAttachments(), m.getFolder(), m.getAccount(), true);

                                        Call<Message> call = service.editMessage(m.getId(), mess );

                                        call.enqueue(new Callback<Message>() {
                                            @Override
                                            public void onResponse(Call<Message> call, Response<Message> response) {
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("messages", m);

                                                Intent i = new Intent(EmailsActivity.this, EmailActivity.class);
                                                i.removeExtra("messages");
                                                i.putExtras(bundle);
                                                startActivity(i);

                                            }

                                            @Override
                                            public void onFailure(Call<Message> call, Throwable t) {

                                            }
                                        });
                                    } else {

                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("messages", m);

                                        Intent i = new Intent(EmailsActivity.this, EmailActivity.class);
                                        i.putExtras(bundle);
                                        startActivity(i);


                                    }
                                }

                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                        Toast.makeText(EmailsActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
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
