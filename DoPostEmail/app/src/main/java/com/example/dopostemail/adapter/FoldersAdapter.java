package com.example.dopostemail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Folder;

import java.util.List;

public class FoldersAdapter extends BaseAdapter {

    private Context mContext;
    private List<Folder> folderList;

    public FoldersAdapter(Context mContext, List<Folder> folderList) {
        this.mContext = mContext;
        this.folderList = folderList;
    }

    public int getCount() {
        return folderList.size();
    }

    @Override
    public Object getItem(int position) {
        return folderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.activity_listview, null);

        TextView fTitle = view.findViewById(R.id.title);
        fTitle.setText(folderList.get(position).getName());

        view.setTag(folderList.get(position).getId());
        return view;
    }
}
