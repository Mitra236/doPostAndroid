package com.example.dopostemail.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.dopostemail.R;

public class EmailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Email");

        setContentView(R.layout.activity_email);

        Toolbar toolbar = findViewById(R.id.toolbar_email);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Utils.darkenStatusBar(this, R.color.colorToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_replay:
                Toast.makeText(getApplicationContext(), "Replied", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(getApplicationContext(), "Message deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_replay_toAll:
                Toast.makeText(getApplicationContext(), "Replied to all", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_attach:
                Toast.makeText(getApplicationContext(), "Files Attached", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_forward:
                Toast.makeText(getApplicationContext(), "Forwarded", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
