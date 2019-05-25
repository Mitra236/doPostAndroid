package com.example.dopostemail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Condition;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Operation;
import com.example.dopostemail.model.Rule;
import com.example.dopostemail.server.FoldersInterface;
import com.example.dopostemail.server.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


        Button btnCreate = findViewById(R.id.button_save_cf);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
                Folder folder = new Folder(folderName.getText().toString(), new ArrayList<Folder>(), new ArrayList<Message>(), new Rule());
                Call<Folder> call = service.addFolder(folder);

                call.enqueue(new Callback<Folder>() {
                    @Override
                    public void onResponse(Call<Folder> call, Response<Folder> response) {
                        Toast.makeText(CreateFolderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Folder> call, Throwable t) {
                        Toast.makeText(CreateFolderActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FoldersInterface service = RetrofitClient.getClient().create(FoldersInterface.class);
//
//                //---------------
//                RadioButton radioMove = findViewById(R.id.radioMove);
//                RadioButton radioCopy = findViewById(R.id.radioCopy);
//                RadioButton radioDelete = findViewById(R.id.radioDelete);
//
//                RadioButton radioTo = findViewById(R.id.radioTo);
//                RadioButton radioFrom = findViewById(R.id.radioFrom);
//                RadioButton radioCc = findViewById(R.id.radioCc);
//                RadioButton radioSubject = findViewById(R.id.radioSubject);
//
//                Condition condition = Condition.TO;
//                Operation operation = Operation.MOVE;
//
//                if(radioTo.isChecked() == true){
//                    condition = Condition.TO;
//                }else if(radioFrom.isChecked() == true){
//                    condition = Condition.FROM;
//                }else if(radioCc.isChecked() == true){
//                    condition = Condition.CC;
//                }else if(radioSubject.isChecked() == true){
//                    condition = Condition.SUBJECT;
//                }
//
//                if(radioMove.isChecked() == true){
//                    operation = Operation.MOVE;
//                }else if(radioCopy.isChecked() == true){
//                    operation = Operation.COPY;
//                }else if(radioDelete.isChecked() == true){
//                    operation = Operation.DELETE;
//                }
//                //-----------------
//
//
//                String content = "";
//                content = folderName.getText().toString() + "," + operation.toString() + "," + condition.toString();
//
//                Call<Folder> call = service.addFolder(content);
//
//                call.enqueue(new Callback<Folder>() {
//                    @Override
//                    public void onResponse(Call<Folder> call, Response<Folder> response) {
//                        Toast.makeText(CreateFolderActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Folder> call, Throwable t) {
////                        Toast.makeText(FolderActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                    }
//                });
 //           }
  //      });


    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

//        Button save = (Button)findViewById(R.id.button_save_cf);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String f = folderName.getText().toString().trim();
//
//                if(f.equals("")) {
//                    Toast.makeText(CreateFolderActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(CreateFolderActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

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
