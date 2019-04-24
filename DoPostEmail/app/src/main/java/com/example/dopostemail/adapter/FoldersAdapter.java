package com.example.dopostemail.adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dopostemail.R;

import com.example.dopostemail.activities.FolderActivity;
import com.example.dopostemail.model.Folder;
import java.util.ArrayList;

public class FoldersAdapter extends ArrayAdapter<Folder> {

    private Context context;
    private int resource;
    private ArrayList<Folder> folders;

    public FoldersAdapter(Context context, int resource, ArrayList<Folder> folders) {
        super(context, resource, folders);
        this.context = context;
        this.resource = resource;
        this.folders = folders;
    }


    @Override
    public View getView(final int position,View convertView,ViewGroup parent) {

        View view = View.inflate(context, R.layout.activity_listview, null);

        TextView fTitle = view.findViewById(R.id.title);
        fTitle.setText(folders.get(position).getName());

        view.setTag(folders.get(position).getId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(context.getApplicationContext(), FolderActivity.class);
                e.putExtra("folder", position);
                context.startActivity(e);
            }
        });

        return view;

//        TextView txtNaslov = convertView.findViewById(R.id.folder_name);
//        txtNaslov.setText(f.getName());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent e = new Intent(context.getApplicationContext(), FolderActivity.class);
//                e.putExtra("folder", position);
//                context.startActivity(e);
//            }
//        });


    }
}

