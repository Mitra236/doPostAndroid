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
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEmailActivity extends AppCompatActivity {

    ArrayList<Contact> to = new ArrayList<>();
    ArrayList<Contact> cc = new ArrayList<>();
    ArrayList<Contact> bcc = new ArrayList<>();

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
                for(Contact con : m.getTo()){
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
                ArrayList<Contact> contacts1 = response.body();

                ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(CreateEmailActivity.this, android.R.layout.simple_dropdown_item_1line, contacts1);
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
                        Contact selected = (Contact) parent.getAdapter().getItem(position);
                        to.add(selected);
                    }
                });

                mCc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contact selected = (Contact) parent.getAdapter().getItem(position);
                        cc.add(selected);
                    }
                });

                mBcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contact selected = (Contact) parent.getAdapter().getItem(position);
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

                ContactsInterface serviceCon = RetrofitClient.getClient().create(ContactsInterface.class);
                Call<ArrayList<Contact>> callCon = serviceCon.getContacts();

                callCon.enqueue(new Callback<ArrayList<Contact>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                        final ArrayList<Contact> allContacts = response.body();


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
                            String params = "";

                            String contactId = "";

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                            String currentUser = pref.getString("loggedInUser", "");
                            int userId = pref.getInt("userId", 0);



                            for(Contact con : allContacts){

                                if(currentUser.equals(con.getEmail())){
                                    contactId = String.valueOf(con.getId());
                                }
                            }

                            String toString = "", ccString = "", bccString = "";
                            for(Contact con : to){
                                toString += con.getId();
                            }
                            for(Contact con : cc){
                                ccString += con.getId();
                            }
                            for(Contact con : bcc){
                                bccString += con.getId();
                            }
                        params = contactId + "," + toString + "," + ccString + "," + bccString + "," + "dateTime" + "," + subject + "," +
                                content + "," + "name1.name2" + "," + "data|type|name.data|type|name" + "," + "3" + "," + String.valueOf(userId);


                        Call<Message> callM = service.addMessage(params);

                        callM.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                Toast.makeText(CreateEmailActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(CreateEmailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                            }


                        }

                        @Override
                        public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                            Toast.makeText(CreateEmailActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
                        }

                    });
                Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_cancel:
                ContactsInterface serviceCon2 = RetrofitClient.getClient().create(ContactsInterface.class);
                Call<ArrayList<Contact>> callCon2 = serviceCon2.getContacts();

                callCon2.enqueue(new Callback<ArrayList<Contact>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                        final ArrayList<Contact> allContacts = response.body();


                        EditText subjectTB = findViewById(R.id.editSubject);
                        EditText contentTB = findViewById(R.id.editMessage);
                        String subject = subjectTB.getText().toString();
                        String content = contentTB.getText().toString();


                            MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                            String params = "";

                            String contactId = "";

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                            String currentUser = pref.getString("loggedInUser", "");
                            int userId = pref.getInt("userId", 0);



                            for(Contact con : allContacts){

                                if(currentUser.equals(con.getEmail())){
                                    contactId = String.valueOf(con.getId());
                                }
                            }

                            String toString = "", ccString = "", bccString = "";
                            for(Contact con : to){
                                toString += con.getId();
                                toString += "|";

                            }
                            for(Contact con : cc){
                                ccString += con.getId();
                                ccString += "|";
                            }
                            for(Contact con : bcc){
                                bccString += con.getId();
                                bccString += "|";
                            }

                            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date(System.currentTimeMillis());
                            String nowString = date.toString();

                            params = contactId + "," + toString + "," + ccString + "," + bccString + "," + nowString + "," + subject + "," +
                                    content + "," + "name1.name2" + "," + "data|type|name.data|type|name" + "," + "3" + "," + String.valueOf(userId);


                            Call<Message> callM = service.draftMessage(params);

                            callM.enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    Toast.makeText(CreateEmailActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Toast.makeText(CreateEmailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });



                    }

                    @Override
                    public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                        Toast.makeText(CreateEmailActivity.this, "Something unexpectedly expected happened", Toast.LENGTH_SHORT).show();
                    }

                });
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
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
