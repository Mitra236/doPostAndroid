package com.example.dopostemail.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.RetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Contact");
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactActivity.this, ContactsActivity.class);
                startActivity(i);
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        Bundle bundle = getIntent().getExtras();
        final Contact c = (Contact) bundle.getSerializable("contacts");

        ImageView img = findViewById(R.id.contact_icon);
        img.setImageResource(c.getPhoto().getPath());

        final EditText tbFirstName = findViewById(R.id.firstNameEdit);
        tbFirstName.setText(c.getFirstName());

        final EditText tbLastName = findViewById(R.id.lastNameEdit);
        tbLastName.setText(c.getLastName());

        final EditText tbUsername = findViewById(R.id.usernameEdit);
        tbUsername.setText(c.getDisplay());

        final EditText tbEmail = findViewById(R.id.emailEdit);
        tbEmail.setText(c.getEmail());

        EditText tbFormat = findViewById(R.id.formatEdit);
        tbFormat.setText(c.getFormat().toString());

//        TextView twTo = findViewById(R.id.con_to);
//        TextView twFrom = findViewById(R.id.con_from);
//        TextView twCc = findViewById(R.id.con_cc);
//        TextView twBcc = findViewById(R.id.con_bcc);

        Button btnDelete = findViewById(R.id.button_delete_c);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);

                builder.setTitle("Confirm");

                builder.setMessage("Are you sure that you want to delete contact?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
                        Call<Contact> call = service.deleteContact(c.getId());

                        call.enqueue(new Callback<Contact>() {
                            @Override
                            public void onResponse(Call<Contact> call, Response<Contact> response) {
                                Toast.makeText(ContactActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ContactActivity.this, ContactsActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Contact> call, Throwable t) {
                                Toast.makeText(ContactActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ContactActivity.this, ContactsActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



        Button btnSave = findViewById(R.id.button_save_c);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tbFirstName.getText().toString();
                String lastName = tbLastName.getText().toString();
                String display = tbUsername.getText().toString();
                String email = tbEmail.getText().toString();
                
                if (TextUtils.isEmpty(name)) {
                    tbFirstName.setError(getString(R.string.edit_name));
                    tbFirstName.requestFocus();
                } else if (TextUtils.isEmpty(lastName)) {
                    tbLastName.setError(getString(R.string.edit_lastname));
                    tbLastName.requestFocus();
                } else if (TextUtils.isEmpty(display)) {
                    tbUsername.setError(getString(R.string.edit_display));
                    tbUsername.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    tbEmail.setError(getString(R.string.edit_email));
                    tbEmail.requestFocus();
                } else {
                    ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
                    String params = "";
                    params = Integer.toString(c.getId()) + "," + tbFirstName.getText().toString() + "," +
                            tbLastName.getText().toString() + "," + tbUsername.getText().toString() + "," + tbEmail.getText().toString() + "," + "HTML";

                    Call<Contact> call = service.editContact(params);

                    call.enqueue(new Callback<Contact>() {
                        @Override
                        public void onResponse(Call<Contact> call, Response<Contact> response) {
                            Toast.makeText(ContactActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ContactActivity.this, ContactActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Contact> call, Throwable t) {
                            Toast.makeText(ContactActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

//        StringBuilder builder1 = new StringBuilder();
//        builder1.append("To: ");
//        for (Message me : c.getTo()) {
//            twTo.setText(builder1.append(me.getSubject()  + ", "));
//        }
//
//        StringBuilder builder2 = new StringBuilder();
//        builder2.append("From: ");
//        for (Message me : c.getFrom()) {
//            twFrom.setText(builder2.append(me.getSubject()  + ", "));
//        }
//
//        StringBuilder builder3 = new StringBuilder();
//        builder3.append("Cc: ");
//        for (Message me : c.getCc()) {
//            twCc.setText(builder3.append(me.getSubject() + ", "));
//        }
//        StringBuilder builder4 = new StringBuilder();
//        builder4.append("Bcc: ");
//        for (Message me : c.getBcc()) {
//            twBcc.setText(builder4.append(me.getSubject() + ", "));
//        }
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
