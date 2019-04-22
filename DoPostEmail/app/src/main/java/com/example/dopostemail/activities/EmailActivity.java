package com.example.dopostemail.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dopostemail.R;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Format;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;


public class EmailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Email");

        setContentView(R.layout.activity_email);

        Toolbar toolbar = findViewById(R.id.toolbar_email);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailActivity.this, EmailsActivity.class);
                startActivity(i);
            }
        });


        Utils.darkenStatusBar(this, R.color.colorToolbar);

        TextView subject = findViewById(R.id.textSubject);
        TextView dateTime = findViewById(R.id.textDate);
        TextView from = findViewById(R.id.textFrom);
        TextView message = findViewById(R.id.textMessage);
        TextView tag = findViewById(R.id.textTag);
        TextView attachment = findViewById(R.id.textAttachment);
        TextView to = findViewById(R.id.textTo);
        TextView cc = findViewById(R.id.textCC);
        TextView bcc = findViewById(R.id.textBcc);

        Bundle bundle = getIntent().getExtras();
        Message m = (Message)bundle.getSerializable("messages");

        subject.setText("Subject: " + m.getSubject());

        StringBuilder builder3 = new StringBuilder();
        builder3.append("To: ");
        for(Contact me: m.getTo()) {
            to.setText(builder3.append(me.getFirstName() + " "));
        }

        StringBuilder builder4 = new StringBuilder();
        builder4.append("Cc: ");
        for(Contact me: m.getCc()) {

                cc.setText(builder4.append(me.getFirstName() + ", "));

        }

        StringBuilder builder5 = new StringBuilder();
        builder5.append("Bcc: ");
        for(Contact me: m.getBcc()) {
                bcc.setText(builder5.append(me.getFirstName() + ", "));

        }

        StringBuilder builder = new StringBuilder();
        builder.append("Tags: ");
        for(Tag me: m.getTag()) {
            tag.setText(builder.append(me.getName() + ", "));
        }

        StringBuilder builder2 = new StringBuilder();
        builder2.append("Attachments: ");
        for(Attachment a: m.getAttachments()){
            attachment.setText(builder2.append(a.getName() + ", "));
        }

        dateTime.setText("Date: " + m.getDateTime());
        from.setText("From: " + m.getFrom().getFirstName());
        message.setText(m.getContent());

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
                Toast.makeText(getApplicationContext(), "Attachment saved", Toast.LENGTH_SHORT).show();
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
