package com.example.dopostemail.activities;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dopostemail.R;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailActivity extends AppCompatActivity {

    public static Date fromUTC(String dateParam) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateParam);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

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
                i.removeExtra("messages");
                startActivity(i);
                getIntent().removeExtra("messages");
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
        TextView folder = findViewById(R.id.textFolder);
        TextView account = findViewById(R.id.textAccount);

        Bundle bundle = getIntent().getExtras();
        Message m = (Message) bundle.getSerializable("messages");

        if(m != null) {
            subject.setText(m.getSubject());

            StringBuilder builder3 = new StringBuilder();
            builder3.append("To: ");
            for (Contact me : m.getTo()) {
                to.setText(builder3.append(me + ", "));
            }

            StringBuilder builder4 = new StringBuilder();
            builder4.append("Cc: ");
            if (!m.getCc().isEmpty()) {
                for (Contact me : m.getCc()) {
                    cc.setText(builder4.append(me + ", "));
                }

            } else {
                cc.setText(builder4.append("stefi123@gmail.com" + ", "));
            }

            StringBuilder builder5 = new StringBuilder();
            builder5.append("Bcc: ");
            if (!m.getBcc().isEmpty()) {
                for (Contact me : m.getBcc()) {

                    bcc.setText(builder5.append(me + ", "));

                }
            } else {
                bcc.setText(builder5.append("aco123@gmail.com" + ", "));
            }

            StringBuilder builder = new StringBuilder();
            builder.append("Tags: ");
            if (m.getTag() != null) {
                for (Tag me : m.getTag()) {
                    tag.setText(builder.append(me.getName() + ", "));
                }
            }


            StringBuilder builder2 = new StringBuilder();
            builder2.append("Attachments: ");
            if (m.getAttachments() != null) {
                for (Attachment a : m.getAttachments()) {

                    attachment.setText(builder2.append(a.getName() + ", "));
                }
            }else {
                attachment.setText(builder2.append("prijave za 2. kolokvijum.pptx" + ", "));
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = fromUTC(m.getDateTime());


            dateTime.setText("Date: " + dateFormat.format(date));
            from.setText("From: " + m.getFrom());
            message.setText(m.getContent());
            if (m.getFolder() != null) {
                folder.setText("Folder: " + m.getFolder().getName());
            }
            if (m.getAccount() != null) {
                account.setText(m.getAccount().getUsername());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundleMain = getIntent().getExtras();
        final Message m1 = (Message) bundleMain.getSerializable("messages");

        switch (item.getItemId()) {
            case R.id.action_replay:

                Intent intent = new Intent(EmailActivity.this, CreateEmailActivity.class);
                Bundle bundleReply = new Bundle();
                bundleReply.putSerializable("message", m1);
                bundleReply.putString("action", "reply");
                intent.removeExtra("message");
                intent.putExtras(bundleReply);
                startActivity(intent);


                return true;
            case R.id.action_delete:

                Bundle bundle = getIntent().getExtras();
                final Message m = (Message) bundle.getSerializable("messages");

                AlertDialog.Builder builder = new AlertDialog.Builder(EmailActivity.this);

                builder.setTitle("Confirm");

                builder.setMessage("Are you sure that you want to delete this message?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                        Call<Message> call = service.deleteMessage(m.getId());

                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                Toast.makeText(EmailActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EmailActivity.this, EmailsActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(EmailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EmailActivity.this, EmailsActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.action_replay_toAll:

                Intent intentReplyAll = new Intent(EmailActivity.this, CreateEmailActivity.class);
                Bundle bundleReplyAll = new Bundle();
                bundleReplyAll.putSerializable("message", m1);
                bundleReplyAll.putString("action", "replyAll");
                intentReplyAll.removeExtra("message");
                intentReplyAll.putExtras(bundleReplyAll);
                startActivity(intentReplyAll);

                return true;
            case R.id.action_attach:
                Toast.makeText(getApplicationContext(), "Attachment saved", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_forward:

                Intent intentForword = new Intent(EmailActivity.this, CreateEmailActivity.class);
                Bundle bundleForword = new Bundle();
                bundleForword.putSerializable("message", m1);
                bundleForword.putString("action", "Forword");
                intentForword.removeExtra("message");
                intentForword.putExtras(bundleForword);
                startActivity(intentForword);

                return true;
            case R.id.action_move_to:


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
