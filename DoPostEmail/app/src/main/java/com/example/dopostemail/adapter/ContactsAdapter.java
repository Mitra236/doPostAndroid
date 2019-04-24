package com.example.dopostemail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Message;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private Context cContext;
    private List<Contact> contactList;

    public ContactsAdapter(Context cContext, List<Contact> contactList) {
        this.cContext = cContext;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
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

        mTitle.setText(contactList.get(position).getDisplay());
        mSubTitle.setText(contactList.get(position).getEmail());
        img.setImageResource(contactList.get(position).getPhoto().getPath());
<<<<<<< HEAD

=======
>>>>>>> 59a578e113e253a1134a47046c21aefe00625307

//        mTitle.setText(contactList.get(position).getFrom().getDisplay());
//        mSubTitle.setText(contactList.get(position).getDateTime());

        view.setTag(contactList.get(position).getId());
        return view;
    }
}
