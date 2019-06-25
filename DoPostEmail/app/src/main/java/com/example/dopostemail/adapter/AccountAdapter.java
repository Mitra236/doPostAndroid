package com.example.dopostemail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Contact;

import java.util.List;

public class AccountAdapter  extends BaseAdapter {
    private Context cContext;
    private List<Account> accountList;

    public AccountAdapter(Context cContext, List<Account> accList) {
        this.cContext = cContext;
        this.accountList = accList;
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = View.inflate(cContext, R.layout.activity_listview, null);

        TextView mTitle = view.findViewById(R.id.title);
        TextView mSubTitle = view.findViewById(R.id.subTitle);
        ImageView img = view.findViewById(R.id.icon);

        mTitle.setText(accountList.get(position).getDisplayname());
        mSubTitle.setText(accountList.get(position).getUsername());
        img.setImageResource(R.drawable.contacts_icon);

        view.setTag(accountList.get(position).getId());
        return view;
    }
}
