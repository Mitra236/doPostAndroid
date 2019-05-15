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

public class CreateFolderActivity extends AppCompatActivity {

    private EditText folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Folder");
        setContentView(R.layout.activity_create_folder);

        Toolbar toolbar = findViewById(R.id.toolbar_create_folder);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateFolderActivity.this, FoldersActivity.class);
                startActivity(i);
            }
        });

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        folderName = findViewById(R.id.folder_name);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Button save = (Button)findViewById(R.id.button_save_cf);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f = folderName.getText().toString().trim();

                if(f.equals("")) {
                    Toast.makeText(CreateFolderActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CreateFolderActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button cancel = (Button)findViewById(R.id.button_cancel_cf);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateFolderActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
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
