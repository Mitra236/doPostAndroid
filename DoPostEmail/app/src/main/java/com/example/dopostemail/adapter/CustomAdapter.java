package com.example.dopostemail.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private List<Message> messageList;

    public CustomAdapter(Context mContext, List<Message> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = View.inflate(mContext, R.layout.activity_listview, null);

        TextView mTitle = view.findViewById(R.id.title);
        TextView mSubTitle = view.findViewById(R.id.subTitle);
        TextView mDate = view.findViewById(R.id.date);
        if(messageList.get(position).isMessageRead() == false) {
            mSubTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            mDate.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        mTitle.setText(messageList.get(position).getFrom().getFirstName());
        mSubTitle.setText(messageList.get(position).getContent());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        mDate.setText(dateFormat.format(messageList.get(position).getDateTime()));



        view.setTag(messageList.get(position).getId());
        return view;
    }
}
