package com.example.dopostemail.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.dopostemail.model.Message;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dopostemail.R;
import com.example.dopostemail.adapter.CustomAdapter;
import com.example.dopostemail.server.MessagesInterface;
import com.example.dopostemail.server.RetrofitClient;


import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends PreferenceActivity {

    private ArrayList<Message> messages;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Settings");
        addPreferencesFromResource(R.xml.preferences);
        Utils.darkenStatusBar(this, R.color.colorToolbar);
        CheckBoxPreference check = (CheckBoxPreference)findPreference("sort_ascending");
//        check.setChecked(false);


        final CheckBoxPreference descending=(CheckBoxPreference) findPreference("sort_descending");
        descending.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(descending.isChecked()) {
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<ArrayList<Message>> call = service.getAllMessagesDesc();
                    CheckBoxPreference checkA = (CheckBoxPreference)findPreference("sort_ascending");
                    checkA.setChecked(false);

                call.enqueue(new Callback<ArrayList<Message>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                        messages = response.body();
                        ListView lw = findViewById(R.id.list_view_emails);
                        CustomAdapter mAdapter = new CustomAdapter(getApplicationContext(), messages);
                        Intent folders = new Intent(SettingsActivity.this, EmailsActivity.class);
                        startActivity(folders);

                        }
                    @Override
                    public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "Something went wrong...Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                return true;
            }
        });

        final CheckBoxPreference asc=(CheckBoxPreference) findPreference("sort_ascending");
        asc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(asc.isChecked()){
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<ArrayList<Message>> call = service.getAllMessagesAsc();
                CheckBoxPreference checkD = (CheckBoxPreference)findPreference("sort_descending");
                checkD.setChecked(false);

                call.enqueue(new Callback<ArrayList<Message>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                        messages= response.body();
                        ListView listView =(ListView)findViewById(R.id.list_view_emails);
                        CustomAdapter mAdapter=new CustomAdapter(getApplicationContext(),messages);
                        Intent folders = new Intent(SettingsActivity.this, EmailsActivity.class);
                        startActivity(folders);
                        }

                    @Override
                    public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "Something went wrong...Please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ListPreference list = (ListPreference) findPreference("pref_refresh_list");
        list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int index = list.findIndexOfValue(newValue.toString());

                if(index != -1){
                    SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("refresh_rate", list.getEntryValues()[index].toString());
                    editor.apply();
                    editor.commit();
                }
                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}