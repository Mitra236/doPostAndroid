package com.example.dopostemail.activities;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Format;
import com.example.dopostemail.model.Photo;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContactActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText display;
    private EditText email;

    private static final int PICK_IMAGE = 100;
    ImageView contactImage;
    Uri imageUri;


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

        contactImage = findViewById(R.id.contact_image);
        Button btnAddImage = findViewById(R.id.buttonAdd);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        Button btnCreate = findViewById(R.id.button_save_cc);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = firstName.getText().toString();
                String lastNameC = lastName.getText().toString();
                String displayC = display.getText().toString();
                String emailC = email.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    firstName.setError(getString(R.string.edit_name));
                    firstName.requestFocus();
                } else if (TextUtils.isEmpty(lastNameC)) {
                    lastName.setError(getString(R.string.edit_lastname));
                    lastName.requestFocus();
                } else if (TextUtils.isEmpty(displayC)) {
                    display.setError(getString(R.string.edit_display));
                    display.requestFocus();
                } else if (TextUtils.isEmpty(emailC) || !Patterns.EMAIL_ADDRESS.matcher(emailC).matches()) {
                    email.setError(getString(R.string.edit_email));
                    email.requestFocus();
                } else {

                    RadioButton formatHTML = findViewById(R.id.radioHTML);

                    String format;
                    if (formatHTML.isChecked() == true) {
                        format = "HTML";
                    } else {
                        format = "PLAIN";
                    }
                    ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
                    Contact contact = new Contact(name, lastNameC, displayC, emailC, Format.HTML, new Photo());
                    Call<Contact> call = service.saveContact(contact);

                    call.enqueue(new Callback<Contact>() {
                        @Override
                        public void onResponse(Call<Contact> call, Response<Contact> response) {
                            Toast.makeText(CreateContactActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateContactActivity.this, ContactsActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Contact> call, Throwable t) {
                            Toast.makeText(CreateContactActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });


    }


    private void openGallery(){
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            Log.i("IMAAGE", imageUri.getPath());

            ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
            File file = new File(imageUri.getPath());

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("images", file.getName(), reqFile);

            Call<Void> call = service.uploadImage(body);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(CreateContactActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CreateContactActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            });

            contactImage.setImageURI(imageUri);
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
