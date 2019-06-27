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



        final CheckBoxPreference descendingDate =(CheckBoxPreference) findPreference("sort_descending_date");
        final CheckBoxPreference ascDate =(CheckBoxPreference) findPreference("sort_ascending_date");
        final CheckBoxPreference descendingSubject =(CheckBoxPreference) findPreference("sort_descending_subject");
        final CheckBoxPreference ascSubject=(CheckBoxPreference) findPreference("sort_ascending_subject");

        descendingSubject.setChecked(false);

        descendingDate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(descendingDate.isChecked()) {
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<ArrayList<Message>> call = service.getSortByDateDesc();
                    ascDate.setChecked(false);
                    descendingSubject.setChecked(false);
                    ascSubject.setChecked(false);

                call.enqueue(new Callback<ArrayList<Message>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                        messages = response.body();

                        Intent intent = new Intent(SettingsActivity.this, EmailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("messy", messages);
                        intent.removeExtra("messy");
                        intent.putExtras(bundle);
                        startActivity(intent);

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


        ascDate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(ascDate.isChecked()){
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                Call<ArrayList<Message>> call = service.getSortByDateAsc();
                    descendingDate.setChecked(false);
                    descendingSubject.setChecked(false);
                    ascSubject.setChecked(false);

                call.enqueue(new Callback<ArrayList<Message>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                        messages= response.body();
                        Intent intent = new Intent(SettingsActivity.this, EmailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("messy", messages);
                        intent.removeExtra("messy");
                        intent.putExtras(bundle);
                        startActivity(intent);
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

        descendingSubject.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(descendingSubject.isChecked()){
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                    Call<ArrayList<Message>> call = service.getSortBySubjectDesc();
                    descendingDate.setChecked(false);
                    ascDate.setChecked(false);
                    ascSubject.setChecked(false);

                    call.enqueue(new Callback<ArrayList<Message>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                            messages= response.body();
                            Intent intent = new Intent(SettingsActivity.this, EmailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("messy", messages);
                            intent.removeExtra("messy");
                            intent.putExtras(bundle);
                            startActivity(intent);
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

        ascSubject.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(ascSubject.isChecked()){
                    MessagesInterface service = RetrofitClient.getClient().create(MessagesInterface.class);
                    Call<ArrayList<Message>> call = service.getSortBySubjectAsc();
                    descendingDate.setChecked(false);
                    descendingSubject.setChecked(false);
                    ascDate.setChecked(false);

                    call.enqueue(new Callback<ArrayList<Message>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                            messages= response.body();
                            Intent intent = new Intent(SettingsActivity.this, EmailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("messy", messages);
                            intent.removeExtra("messy");
                            intent.putExtras(bundle);
                            startActivity(intent);
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