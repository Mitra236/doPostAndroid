package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Format;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContactActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText display;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Create contact");
        setContentView(R.layout.activity_create_contact);

        Toolbar toolbar = findViewById(R.id.toolbar_create_contact);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateContactActivity.this, ContactsActivity.class);
                startActivity(i);
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        firstName = findViewById(R.id.firstNameEdit);
        lastName = findViewById(R.id.lastNameEdit);
        display = findViewById(R.id.usernameEdit);
        email = findViewById(R.id.emailEdit);




        Button btnCreate = findViewById(R.id.button_save_cc);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
                String content = "";
                RadioButton formatHTML = findViewById(R.id.radioHTML);

                String format;
                if(formatHTML.isChecked() == true) {
                    format = "HTML";
                }else {
                    format = "PLAIN";
                }

                content = firstName.getText().toString() + "," + lastName.getText().toString() + "," + display.getText().toString() + ","
                            + email.getText().toString() + "," + format;

                Call<Contact> call = service.addContact(content);

                call.enqueue(new Callback<Contact>() {
                    @Override
                    public void onResponse(Call<Contact> call, Response<Contact> response) {
                        Toast.makeText(CreateContactActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Contact> call, Throwable t) {
//                        Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

//        Button save = (Button)findViewById(R.id.button_save_cc);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(CreateContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                String f = firstName.getText().toString();
//                String l = lastName.getText().toString();
//                String d = display.getText().toString();
//                String e = email.getText().toString();
//                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//                if(f.equals("") || l.equals("") || e.equals("")) {
//                    Toast.makeText(CreateContactActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
//
//                }else if(!e.matches(emailPattern)) {
//                    Toast.makeText(CreateContactActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
//                } else {
//                    final Contact contact = new Contact(6, f, l, d, e, Format.PLAIN);
//
//                    ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
//                    String content = "";
//                    RadioButton formatHTML = findViewById(R.id.radioHTML);
//
//                String format;
//                if(formatHTML.isChecked() == true) {
//                    format = "HTML";
//                }else {
//                    format = "PLAIN";
//                }
//
//                    content = firstName.getText().toString() + "," + lastName.getText().toString() + "," + display.getText().toString() + ","
//                            + email.getText().toString() + "," + format;
//
////                    content.trim();
//
////                    JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
//
//                    Call<Contact> call = service.addContact(content);
//
//                    call.enqueue(new Callback<Contact>() {
//                        @Override
//                        public void onResponse(Call<Contact> call, Response<Contact> response) {
//                            Toast.makeText(CreateContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Contact> call, Throwable t) {
//                            Toast.makeText(CreateContactActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//
//        Button back = (Button)findViewById(R.id.button_back_cc);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(CreateContactActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//
//            }
//        });

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
