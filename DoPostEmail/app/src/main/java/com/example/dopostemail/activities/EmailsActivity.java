package com.example.dopostemail.activities;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dopostemail.R;

public class EmailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Emails");
        setContentView(R.layout.activity_posts);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Button create_email = (Button)findViewById(R.id.button_create_email);
        create_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, CreateEmailActivity.class);
                startActivity(i);

            }
        });

        Button create_folder = (Button)findViewById(R.id.button_create_folder);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, CreateFolderActivity.class);
                startActivity(i);

            }
        });

        Button email = (Button)findViewById(R.id.button_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, EmailActivity.class);
                startActivity(i);

            }
        });

        Button folders = (Button)findViewById(R.id.button_folders);
        folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, FoldersActivity.class);
                startActivity(i);

            }
        });

        Button folder = (Button)findViewById(R.id.button_folder);
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, FolderActivity.class);
                startActivity(i);

            }
        });

        Button settings = (Button)findViewById(R.id.button_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, SettingsActivity.class);
                startActivity(i);

            }
        });

        Button profile = (Button)findViewById(R.id.button_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });

        Button contacts = (Button)findViewById(R.id.button_contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, ContactsActivity.class);
                startActivity(i);

            }
        });

        Button create_contact = (Button)findViewById(R.id.button_create_contact);
        create_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, CreateContactActivity.class);
                startActivity(i);

            }
        });

        Button contact = (Button)findViewById(R.id.button_contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailsActivity.this, ContactActivity.class);
                startActivity(i);

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
