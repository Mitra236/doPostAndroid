package com.example.dopostemail.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dopostemail.R;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;
import com.example.dopostemail.server.TagsInterface;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FolderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contact");
        setContentView(R.layout.activity_folder);

        Toolbar toolbar = findViewById(R.id.toolbar_folder);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        final Folder f = (Folder) bundle.getSerializable("folder");

        final EditText tbFolderName = findViewById(R.id.folder_name);
        tbFolderName.setText(f.getName());

        Button btnDelete = findViewById(R.id.button_delete_f);

        btnDelete.setVisibility(View.VISIBLE);
        if(f.getName().equals("Drafts")){
            btnDelete.setVisibility(View.GONE);
        }else {

            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FolderActivity.this);

                    builder.setTitle("Confirm");

                    builder.setMessage("Are you sure that you want to delete contact?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                            Call<Void> call = service.deleteFolder(f.getId());

                            Log.e("Id", String.valueOf(f.getId()));

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(FolderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
//                                    startActivity(i);
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        }

        Button btnEdit = findViewById(R.id.button_edit_f);

        btnEdit.setVisibility(View.VISIBLE);
        if(f.getName().equals("Drafts")){
            btnEdit.setVisibility(View.GONE);
        }else {
            btnEdit.setVisibility(View.VISIBLE);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("userInfo", 0);
                    String json = prefs.getString("accObject", "");

                    Gson gson = new Gson();
                    final Account acc = gson.fromJson(json, Account.class);

                    FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                    Folder folder = new Folder(f.getId(), tbFolderName.getText().toString(), f.getParentFolder(), f.getMessages(), f.getRule(), acc);
                    Call<Folder> call = service.updateFolder(folder,f.getId());

                    call.enqueue(new Callback<Folder>() {
                        @Override
                        public void onResponse(Call<Folder> call, Response<Folder> response) {
                            Toast.makeText(FolderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Folder> call, Throwable t) {
                            Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
        Call<ArrayList<Folder>> call = service.findSubFolders(f);

        call.enqueue(new Callback<ArrayList<Folder>>() {
            @Override
            public void onResponse(Call<ArrayList<Folder>> call, Response<ArrayList<Folder>> response) {
                ArrayList<Folder> folders = response.body();

                f.setFolders(folders);

                ListView list_subflders = findViewById(R.id.list_view_subfolders);
                FolderAdapter fa = new FolderAdapter(getApplicationContext(), f.getFolders());

                Log.e("Number of subfolders is" , String.valueOf(f.getFolders().size()));

                list_subflders.setAdapter(fa);

                list_subflders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Folder m = f.getFolders().get(position);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("folder", m);

                        Intent i = new Intent(FolderActivity.this, FolderActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);


                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Folder>> call, Throwable t) {
            }
        });

        MessagesInterface service2 = RetrofitClient.getClient().create(MessagesInterface.class);
        Call<ArrayList<Message>> call2 = service2.findFolderMessages(f);

        call2.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                ArrayList<Message> msgs = response.body();

                f.setMessages(msgs);

                ListView list_emails = findViewById(R.id.list_view_emails);
                CustomAdapter ca = new CustomAdapter(getApplicationContext(), f.getMessages());
                list_emails.setAdapter(ca);
                list_emails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Message m = f.getMessages().get(position);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("messages", m);

                        Intent i = new Intent(FolderActivity.this, EmailActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);


                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
            }
        });





    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume()
    {
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
