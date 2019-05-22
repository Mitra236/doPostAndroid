package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
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

                    }
                });

                mCc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

                mBcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        Button btnCreate = findViewById(R.id.button_save_cc);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = firstName.getText().toString();
//                String lastNameC = lastName.getText().toString();
//                String displayC = display.getText().toString();
//                String emailC = email.getText().toString();

//                if (TextUtils.isEmpty(name)) {
//                    firstName.setError(getString(R.string.edit_name));
//                    firstName.requestFocus();
//                } else if (TextUtils.isEmpty(lastNameC)) {
//                    lastName.setError(getString(R.string.edit_lastname));
//                    lastName.requestFocus();
//                } else if (TextUtils.isEmpty(displayC)) {
//                    display.setError(getString(R.string.edit_display));
//                    display.requestFocus();
//                } else if (TextUtils.isEmpty(emailC) || !Patterns.EMAIL_ADDRESS.matcher(emailC).matches()) {
//                    email.setError(getString(R.string.edit_email));
//                    email.requestFocus();
//                } else {
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                    String content = "";
//                    RadioButton formatHTML = findViewById(R.id.radioHTML);
//
//                    String format;
//                    if (formatHTML.isChecked() == true) {
//                        format = "HTML";
//                    } else {
//                        format = "PLAIN";
//                    }

                    content = "";

                    Call<Message> call = service.addMessage(content);

                    call.enqueue(new Callback<Message>() {
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
//                }
            }
        });

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
