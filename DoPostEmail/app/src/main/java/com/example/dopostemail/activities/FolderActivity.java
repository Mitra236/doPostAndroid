package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class FolderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contact");
        setContentView(R.layout.activity_folder);

//        Toolbar toolbar = findViewById(R.id.nav_toolbar_folders);
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(FolderActivity.this, FoldersActivity.class);
//                startActivity(i);
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        final Folder f = (Folder) bundle.getSerializable("folder");

        final EditText tbFolderName = findViewById(R.id.folder_name);
        tbFolderName.setText(f.getName());

        Button btnDelete = findViewById(R.id.button_delete_f);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                Call<Folder> call = service.deleteFolder(f.getId());

                call.enqueue(new Callback<Folder>() {
                    @Override
                    public void onResponse(Call<Folder> call, Response<Folder> response) {
                        Toast.makeText(FolderActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Folder> call, Throwable t) {
//                        Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button btnEdit = findViewById(R.id.button_edit_f);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Folder edit_folder = new Folder();
                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
//                edit_folder.setName(tbFolderName.getText().toString());
//                ArrayList<String> params = new ArrayList<>();
                String content = "";
                content = Integer.toString(f.getId()) + "," + tbFolderName.getText().toString();
//                params.add(Integer.toString(f.getId()));
//                params.add(tbFolderName.getText().toString());

                Call<Folder> call = service.updateFolder(content);

                call.enqueue(new Callback<Folder>() {
                    @Override
                    public void onResponse(Call<Folder> call, Response<Folder> response) {
                        Toast.makeText(FolderActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Folder> call, Throwable t) {
//                        Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);



//        TextView nazivFoldera = findViewById(R.id.folder_name);
//        nazivFoldera.setText(f.getName());

        ListView list_subflders = findViewById(R.id.list_view_subfolders);
        ListView list_emails = findViewById(R.id.list_view_emails);

//        FolderAdapter fa = new FolderAdapter(getApplicationContext(), f.getFolders());
//        list_subflders.setAdapter(fa);

        CustomAdapter ca = new CustomAdapter(getApplicationContext(), f.getMessages());
        list_emails.setAdapter(ca);

//        list_subflders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Folder m = f.getFolders().get(position);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("folder", m);
//
//                Intent i = new Intent(FolderActivity.this, FolderActivity.class);
//                i.putExtras(bundle);
//                startActivity(i);
//
//
//            }
//        });

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

//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        int position = b != null ? (int) b.get("folder"): -1;
//
//        Dummy d = new Dummy();
//        Folder folder = position != -1 ? d.getFolders().get(position): new Folder();
//
//        TextView nazivFoldera = findViewById(R.id.folder_name);
//        nazivFoldera.setText(folder.getName());
//
////        ListView mList = findViewById(R.id.list_view);
//        ListView mList = findViewById(R.id.listFolderMessages);
//        ArrayList<Message> messages = folder.getMessages();
//        CustomAdapter ela = new CustomAdapter(getApplicationContext(),messages);
//        mList.setAdapter(ela);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        Button edit = (Button)findViewById(R.id.button_edit_f);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FolderActivity.this, "Edit", Toast.LENGTH_SHORT).show();
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
