package com.example.dopostemail.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.PhotoInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;

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
    Photo photo = new Photo();
    Photo contactPhoto = new Photo();

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

        PhotoInterface servicePhoto = RetrofitClient.getClient().create(PhotoInterface.class);

        Call<ArrayList<Photo>> callPhoto = servicePhoto.getPhotos();



        callPhoto.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                ArrayList<Photo> photos = response.body();

                for(Photo p: photos){
                    if(p.getPath().equals(photo.getPath())){
                        contactPhoto = p;
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

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

                    PhotoInterface servicePhoto = RetrofitClient.getClient().create(PhotoInterface.class);

                    Call<ArrayList<Photo>> callPhoto = servicePhoto.getPhotos();



                    callPhoto.enqueue(new Callback<ArrayList<Photo>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                            ArrayList<Photo> photos = response.body();

                            for(Photo p: photos){
                                if(p.getPath().equals(photo.getPath())){
                                    contactPhoto = p;
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

                        }
                    });


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json = pref.getString("userObject", "");

                    long lon = 5;

                    Gson gson = new Gson();
                    final User loggedInUser = gson.fromJson(json, User.class);

                    ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
                    Contact contact = new Contact();
                    contact.setFirstName(name);
                    contact.setLastName(lastNameC);
                    contact.setDisplay(displayC);
                    contact.setEmail(emailC);
                    contact.setFormat(Format.HTML);
                    contact.setPhoto(contactPhoto);
                //    contact.setPhoto(new Photo(lon, photo.getPath()));
                    contact.setUser(loggedInUser);

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

            ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
            File file = new File( getRealPathFromURI(imageUri));
            String imageName = file.getName();

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("img", imageName, reqFile);


            Call<Void> call = service.uploadImage(body);
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(CreateContactActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CreateContactActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });

            contactImage.setImageURI(imageUri);

            PhotoInterface servicePhoto = RetrofitClient.getClient().create(PhotoInterface.class);

            photo.setPath(imageName);

            Call<Photo> callPhoto = servicePhoto.savePhoto(photo);
            callPhoto.enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    Toast.makeText(CreateContactActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    Toast.makeText(CreateContactActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });

            PhotoInterface servicePhotom = RetrofitClient.getClient().create(PhotoInterface.class);

            Call<ArrayList<Photo>> callPhotmo = servicePhotom.getPhotos();



            callPhotmo.enqueue(new Callback<ArrayList<Photo>>() {
                @Override
                public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                    ArrayList<Photo> photos = response.body();

                    for(Photo p: photos){
                        if(p.getPath().equals(photo.getPath())){
                            contactPhoto = p;
                        }
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

                }
            });
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(contentUri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
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
