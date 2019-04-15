package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Contact;

public class ContactActivity extends AppCompatActivity {

    Contact conTemp = new Contact(1, "Pera", "Peric", "Pex", "pera123@gmail.com", "asd");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Contact");
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        EditText tbFirstName = findViewById(R.id.firstNameEdit);
        tbFirstName.setText(conTemp.getFirstName());

        EditText tbLastName = findViewById(R.id.lastNameEdit);
        tbLastName.setText(conTemp.getLastName());

        EditText tbUsername = findViewById(R.id.usernameEdit);
        tbUsername.setText(conTemp.getDisplay());

        EditText tbEmail = findViewById(R.id.emailEdit);
        tbEmail.setText(conTemp.getEmail());

        EditText tbFormat = findViewById(R.id.formatEdit);
        tbFormat.setText(conTemp.getFormat());

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Button save = (Button)findViewById(R.id.button_save_c);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            }
        });

        Button back = (Button)findViewById(R.id.button_back_c);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "Back", Toast.LENGTH_SHORT).show();

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
