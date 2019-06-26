package com.example.dopostemail.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.graphics.PathUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.Base64;
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
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.ContactsInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.google.gson.Gson;

import java.io.File;
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

    ArrayList<Contact> to = new ArrayList<>();
    ArrayList<Contact> cc = new ArrayList<>();
    ArrayList<Contact> bcc = new ArrayList<>();
    private static final int PICK_FILE = 100;
    public ArrayList<Contact> allContactsUnique = new ArrayList<>();
    Attachment attachment;
    public ArrayList<Attachment> messageAttachments = new ArrayList<>();

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


        Bundle bundle = getIntent().getExtras();

        Message m = new Message();
        String action = "";
        try{
            m = (Message) bundle.getSerializable("message");
            action = bundle.getString("action");
        }catch(Exception ex){}



//        if(m != null){
//            if(action.equals("reply")){
//                subjectCreate.setText(m.getSubject());
//                contentCreate.setText(m.getContent());
//                to.add(m.getFrom());
//                mTo.setText(m.getFrom().toString() + ", ");
//            }else if(action.equals("replyAll")){
//                subjectCreate.setText(m.getSubject());
//                contentCreate.setText(m.getContent());
//                to.add(m.getFrom());
//                mTo.setText(m.getFrom().toString());
//                for(String con : m.getTo()){
//                    to.add(con);
//                    mTo.setText(mTo.getText() + ", " + con.toString());
//                }
//            }else if(action.equals("Forword")){
//                subjectCreate.setText(m.getSubject());
//                contentCreate.setText(m.getContent());
//            }
//        }



        ContactsInterface service = RetrofitClient.getClient().create(ContactsInterface.class);
        Call<ArrayList<Contact>> call = service.getContacts();

        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
//                ArrayList<Contact> contacts1 = response.body();
                allContactsUnique = response.body();
//                ArrayList<String> stringyContacts = new ArrayList<>();
//                for(Contact con : allContactsUnique){
//                    stringyContacts.add(con.getEmail());
//                }

                ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(CreateEmailActivity.this, android.R.layout.simple_dropdown_item_1line, allContactsUnique);
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
//                        String selected = (String) parent.getAdapter().getItem(position);
                        Contact selected = (Contact) parent.getAdapter().getItem(position);
                        to.add(selected);
                    }
                });

                mCc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String selected = (String) parent.getAdapter().getItem(position);
                        Contact selected = (Contact) parent.getAdapter().getItem(position);
                        cc.add(selected);
                    }
                });

                mBcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String selected = (String) parent.getAdapter().getItem(position);
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


                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json = prefs.getString("userObject", "");
                    String json2 = prefs.getString("accObject", "");
                    Gson gson = new Gson();
                    final User loggedInUser = gson.fromJson(json, User.class);
                    final Account loggedInAcc = gson.fromJson(json2, Account.class);

                    if(allContactsUnique.isEmpty()){
                        Log.e("all contacts", "empty");
                    }
                    if(loggedInAcc == null){
                        Log.e("acc", "empty");
                    }


                    Contact sender = new Contact();

                    for(Contact con : allContactsUnique){
                        Log.e("Con: ", con.getEmail());
                        if(loggedInAcc.getUsername().equals(con.getEmail())){
                            try{
                                sender = (Contact) con.clone();
                            }catch(CloneNotSupportedException c){}

                        }
                    }

                    Date date = new Date();
                    String dateStr = toUTC(date);
                    ArrayList<Attachment> atts = new ArrayList<>();
                    ArrayList<Tag> tags = new ArrayList<>();

                    EditText editTextTags = findViewById(R.id.editTags);
                    String tagsString = editTextTags.getText().toString();
                    String[] splitTags = tagsString.split(" ");
                    for(String tagString : splitTags){
                        Tag newTag = new Tag();
                        newTag.setName(tagString);
                        tags.add(newTag);
                    }



                    Folder fold = new Folder();

                    ArrayList<Folder> accountFolders = loggedInAcc.getFolders();
                    for(Folder fol : accountFolders){
                        if(fol.getName().equals("INBOX")){
                            fold = fol;
                        }
                    }


                    Message msg = new Message(sender, to, cc, bcc, dateStr, subject, content, tags, messageAttachments, fold, loggedInAcc, false);
                    Log.e("msg: ", String.valueOf(msg.getFrom().getId()));


                    Call<Void> callM = service.send(msg);

                    callM.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(CreateEmailActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
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


//                        Message msg = new Message(currentUser, to, cc, bcc, dateStr, subject2, content2, tags, atts, fold, acc, false);
//
//
//                            Call<Message> callM = service.draftMessage(msg);
//
//                            callM.enqueue(new Callback<Message>() {
//                                @Override
//                                public void onResponse(Call<Message> call, Response<Message> response) {
//                                    Toast.makeText(CreateEmailActivity.this, "Message drafted", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(CreateEmailActivity.this, EmailsActivity.class);
//                                    startActivity(i);
//                                }
//
//                                @Override
//                                public void onFailure(Call<Message> call, Throwable t) {
//                                    Toast.makeText(CreateEmailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                                }
//                            });

//                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_attach:
                attachFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void attachFile(){
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE);
    }

    String filename;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE){
                String filePath = "";
                if(data == null){

                    return;
                }

                    Uri returnUri = data.getData();
                    Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    filename = returnCursor.getString(nameIndex);
                    filePath = returnUri.getPath();
                    Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();

                    attachment = new Attachment();
                    attachment.setName(filename);
                    attachment.setData(filePath);
                 //   attachment.setId(hashCode());
                    attachment.setMessage(new Message());
                    messageAttachments.add(attachment);


            }
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
