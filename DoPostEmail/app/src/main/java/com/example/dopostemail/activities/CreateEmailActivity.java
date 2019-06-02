package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.ContactsAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEmailActivity extends AppCompatActivity {

    ArrayList<String> to = new ArrayList<>();
    ArrayList<String> cc = new ArrayList<>();
    ArrayList<String> bcc = new ArrayList<>();
    public ArrayList<Contact> allContactsUnique = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);
        setTitle("Compose");

        Toolbar toolbar = findViewById(R.id.toolbar_create_emails);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                startActivity(i);
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        to.clear();
        cc.clear();
        bcc.clear();

        final MultiAutoCompleteTextView mTo = findViewById(R.id.editTo);
        final MultiAutoCompleteTextView mCc = findViewById(R.id.editCC);
        final MultiAutoCompleteTextView mBcc = findViewById(R.id.editBCC);

        EditText subjectCreate = findViewById(R.id.editSubject);
        EditText contentCreate = findViewById(R.id.editMessage);
//        String subject = subjectCreate.getText().toString();
//        String content = contentCreate.getText().toString();

        Bundle bundle = getIntent().getExtras();

        Message m = new Message();
        String action = "";
        try{
            m = (Message) bundle.getSerializable("message");
            action = bundle.getString("action");
        }catch(Exception ex){}



        if(m != null){
            if(action.equals("reply")){
                subjectCreate.setText(m.getSubject());
                contentCreate.setText(m.getContent());
                to.add(m.getFrom());
                mTo.setText(m.getFrom().toString() + ", ");
            }else if(action.equals("replyAll")){
                subjectCreate.setText(m.getSubject());
                contentCreate.setText(m.getContent());
                to.add(m.getFrom());
                mTo.setText(m.getFrom().toString());
                for(String con : m.getTo()){
                    to.add(con);
                    mTo.setText(mTo.getText() + ", " + con.toString());
                }
            }else if(action.equals("Forword")){
                subjectCreate.setText(m.getSubject());
                contentCreate.setText(m.getContent());
            }
        }



        ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
        Call<ArrayList<Contact>> call = service.getContacts();

        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
//                ArrayList<Contact> contacts1 = response.body();
                allContactsUnique = response.body();
                ArrayList<String> stringyContacts = new ArrayList<>();
                for(Contact con : allContactsUnique){
                    stringyContacts.add(con.getEmail());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateEmailActivity.this, android.R.layout.simple_dropdown_item_1line, stringyContacts);
                mTo.setAdapter(adapter);
                mTo.setThreshold(1);
                mTo.setTextColor(Color.GRAY);
                mTo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                mCc.setAdapter(adapter);
                mCc.setThreshold(1);
                mCc.setTextColor(Color.GRAY);
                mCc.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                mBcc.setAdapter(adapter);
                mBcc.setThreshold(1);
                mBcc.setTextColor(Color.GRAY);
                mBcc.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                mTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected = (String) parent.getAdapter().getItem(position);
                        to.add(selected);
                    }
                });

                mCc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected = (String) parent.getAdapter().getItem(position);
                        cc.add(selected);
                    }
                });

                mBcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected = (String) parent.getAdapter().getItem(position);
                        bcc.add(selected);
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                Toast.makeText(CreateEmailActivity.this, "Unexpectedly expected error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_send:

                EditText subjectTB = findViewById(R.id.editSubject);
                EditText contentTB = findViewById(R.id.editMessage);
                String subject = subjectTB.getText().toString();
                String content = contentTB.getText().toString();

                if (TextUtils.isEmpty(subject)) {
                    subjectTB.setError("Subject required");
                    subjectTB.requestFocus();
                } else if (TextUtils.isEmpty(content)) {
                    contentTB.setError("Content required");
                    contentTB.requestFocus();
                } else {
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String currentUser = pref.getString("loggedInUser", "");
                    int userId = pref.getInt("userId", 0);

//                    Contact sender = new Contact();

//                    for(Contact con : allContactsUnique){
//
//                        if(currentUser.equals(con.getEmail())){
//                            sender = con;
//                        }
//                    }
                    Date date = new Date();
                    String dateStr = toUTC(date);
                    ArrayList<Attachment> atts = new ArrayList<>();
                    ArrayList<Tag> tags = new ArrayList<>();

                    SharedPreferences prefUser = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json = prefUser.getString("userObject", "");

                    Gson gson = new Gson();
                    Account acc = gson.fromJson(json, Account.class);

                    Folder fold = new Folder();


                    Message msg = new Message(currentUser, to, cc, bcc, dateStr, subject, content, tags, atts, fold, acc, false);

                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json2 = prefs.getString("userObject", "");

                    Gson gson2 = new Gson();
                    final Account acc2 = gson2.fromJson(json2, Account.class);

                    Call<Message> callM = service.sendMessage(msg);

                    callM.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Toast.makeText(CreateEmailActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(CreateEmailActivity.this, "Message sent (probably)", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                            startActivity(i);
                        }
                    });
                }




//                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_cancel:






                        EditText subjectTB2 = findViewById(R.id.editSubject);
                        EditText contentTB2 = findViewById(R.id.editMessage);
                        String subject2 = subjectTB2.getText().toString();
                        String content2 = contentTB2.getText().toString();


                        MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);


                        SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                        String currentUser = pref.getString("loggedInUser", "");
                        int userId = pref.getInt("userId", 0);

                        Contact sender = new Contact();

                        for(Contact con : allContactsUnique){

                            if(currentUser.equals(con.getEmail())){
                                sender = con;
                            }
                        }
                        Date date = new Date();
                        String dateStr = toUTC(date);
                        ArrayList<Attachment> atts = new ArrayList<>();
                        ArrayList<Tag> tags = new ArrayList<>();

                        SharedPreferences prefUser = getApplicationContext().getSharedPreferences("userInfo", 0);
                        String json = prefUser.getString("userObject", "");

                        Gson gson = new Gson();
                        Account acc = gson.fromJson(json, Account.class);

                        Folder fold = new Folder();


                        Message msg = new Message(currentUser, to, cc, bcc, dateStr, subject2, content2, tags, atts, fold, acc, false);


                            Call<Message> callM = service.draftMessage(msg);

                            callM.enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    Toast.makeText(CreateEmailActivity.this, "Message drafted", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Toast.makeText(CreateEmailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });

//                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
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

    public static String toUTC(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static Date fromUTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
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
