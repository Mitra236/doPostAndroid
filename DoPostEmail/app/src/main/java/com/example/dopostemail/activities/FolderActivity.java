package com.example.dopostemail.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Folder;

public class FolderActivity extends AppCompatActivity {

    Folder folderTemp = new Folder(1, "Folder 1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Folder");
        setContentView(R.layout.activity_folder);

        Toolbar toolbar = findViewById(R.id.toolbar_folder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        TextView tbName = findViewById(R.id.folder_name);
        tbName.setText(folderTemp.getName());
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Button edit = (Button)findViewById(R.id.button_edit_f);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FolderActivity.this, "Edit", Toast.LENGTH_SHORT).show();
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
