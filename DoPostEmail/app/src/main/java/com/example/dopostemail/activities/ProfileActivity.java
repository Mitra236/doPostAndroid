package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.User;
import com.example.dopostemail.server.AccountInterface;
import com.example.dopostemail.server.LoginInterface;
import com.example.dopostemail.server.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Profile");
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        final Account account = (Account) bundle.getSerializable("user");

        final TextView txtUsername = findViewById(R.id.textUserName);
        final TextView txtPass = findViewById(R.id.textPass);

        txtUsername.setText(account.getUsername());
        txtPass.setText(account.getPassword());

        Toolbar toolbar = findViewById(R.id.nav_toolbar_profile);
        setSupportActionBar(toolbar);

        Utils.darkenStatusBar(this, R.color.colorToolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Button change = (Button)findViewById(R.id.button_logout_nav);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newUsername = "";
                String newPassword = "";
                newUsername = txtUsername.getText().toString();
                newPassword = txtPass.getText().toString();

                account.setUsername(newUsername);
                account.setPassword(newPassword);

                AccountInterface service = RetrofitClient.getClient().create(AccountInterface.class);

                Call<Account> callLogin = service.editAccount(account, account.getId());

                callLogin.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProfileActivity.this, PickEmailActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(ProfileActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

            }
        });

        Button delete = (Button)findViewById(R.id.profile_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Deleting account...", Toast.LENGTH_SHORT).show();

                AccountInterface service = RetrofitClient.getClient().create(AccountInterface.class);

                Call<Void> callLogin = service.deleteAccount(account.getId());

                callLogin.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ProfileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Toast.makeText(this, "option selected", Toast.LENGTH_SHORT).show();
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_emails:
                Intent i = new Intent(ProfileActivity.this, EmailsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_contacts:
                Intent j = new Intent(ProfileActivity.this, ContactsActivity.class);
                startActivity(j);
                break;
            case R.id.nav_folders:
                Intent k = new Intent(ProfileActivity.this, FoldersActivity.class);
                startActivity(k);
                break;
            case R.id.nav_settings:
                Intent l = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(l);
                break;
            case R.id.nav_logout:
                Intent m = new Intent(ProfileActivity.this, LoginActivity.class);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SharedPreferences settings = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                settings.edit().remove("currentUser").commit();
                startActivity(m);
                break;
        }


        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart(){
        super.onStart();

//        Button btn = findViewById(R.id.button_logout);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String username = txtUsername.getText().toString().trim();
//                String password = txtPass.getText().toString().trim();
//
//                if(!username.equals("") && !password.equals("")) {
//                    acc.setPassword(password);
//                    acc.setUsername(username);
//
//
//                    LoginInterface service = RetrofitClient.getClient().create(LoginInterface.class);
//                    se
//
//                }
//                }
//        });
    }

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
