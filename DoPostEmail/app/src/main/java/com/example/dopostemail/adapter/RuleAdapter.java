package com.example.dopostemail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Rule;

import java.util.List;

public class RuleAdapter extends BaseAdapter {

    private Context mContext;
    private List<Rule> ruleList;

    public RuleAdapter(Context mContext, List<Rule> messageList) {
        this.mContext = mContext;
        this.ruleList = messageList;
    }

    @Override
    public int getCount() {
        return ruleList.size();
    }

    @Override
    public Object getItem(int position) {
        return ruleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = View.inflate(mContext, R.layout.activity_listview, null);

        TextView mTitle = view.findViewById(R.id.title);


        mTitle.setText(ruleList.get(position).getCondition().toString());



        return view;
    }
}
