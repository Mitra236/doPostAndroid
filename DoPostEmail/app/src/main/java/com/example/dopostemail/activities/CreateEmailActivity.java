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

import java.util.ArrayList;

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

//                ArrayList<Contact> allContacts = new ArrayList<>();

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
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
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
