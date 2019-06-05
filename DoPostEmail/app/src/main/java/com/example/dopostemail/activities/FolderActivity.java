package com.example.dopostemail.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.dopostemail.R;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.adapter.FolderAdapter;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
                            Call<Folder> call = service.deleteFolder(f.getId());

                            call.enqueue(new Callback<Folder>() {
                                @Override
                                public void onResponse(Call<Folder> call, Response<Folder> response) {
                                    Toast.makeText(FolderActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure(Call<Folder> call, Throwable t) {
                                    Toast.makeText(FolderActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
                                    startActivity(i);
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
                    FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                    Folder folder = new Folder(f.getId(), tbFolderName.getText().toString(), f.getFolders(), f.getMessages(), f.getRule());
                    Call<Folder> call = service.updateFolder(f.getId(), folder);

                    call.enqueue(new Callback<Folder>() {
                        @Override
                        public void onResponse(Call<Folder> call, Response<Folder> response) {
                            Toast.makeText(FolderActivity.this, "Success", Toast.LENGTH_SHORT).show();
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


        ListView list_subflders = findViewById(R.id.list_view_subfolders);
        ListView list_emails = findViewById(R.id.list_view_emails);

        FolderAdapter fa = new FolderAdapter(getApplicationContext(), f.getFolders());
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
