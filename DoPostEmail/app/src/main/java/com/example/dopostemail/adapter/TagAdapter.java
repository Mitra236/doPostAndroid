package com.example.dopostemail.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.activities.FolderActivity;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends BaseAdapter {

    private Context tContext;
    private List<Tag> tagList;

    public TagAdapter(Context cContext, List<Tag> tagList) {
        this.tContext = cContext;
        this.tagList = tagList;
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public Object getItem(int position) {
        return tagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("Position in adapter", String.valueOf(position));

        View view = View.inflate(tContext, R.layout.activity_listview, null);

        TextView mTitle = view.findViewById(R.id.title);
        TextView mSubTitle = view.findViewById(R.id.subTitle);
        ImageView img = view.findViewById(R.id.icon);

        mTitle.setText(tagList.get(position).getName());
//        mSubTitle.setText(accountList.get(position).getUsername());
        img.setImageResource(R.drawable.ic_edit);

        view.setTag(tagList.get(position).getId());
        return view;
    }
}
