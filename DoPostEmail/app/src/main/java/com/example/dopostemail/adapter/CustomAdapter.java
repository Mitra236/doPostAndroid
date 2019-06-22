package com.example.dopostemail.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dopostemail.R;
import com.example.dopostemail.model.Attachment;
import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<Message> messageList;
    private List<Message> messageFilteredList;

    public CustomAdapter(Context mContext, List<Message> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        this.messageFilteredList = new ArrayList<>(messageList);
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

    public static Date fromUTC(String dateParam) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);

        try {
            return df.parse(dateParam);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = View.inflate(mContext, R.layout.activity_listview, null);

        TextView mTitle = view.findViewById(R.id.title);
        TextView mSubTitle = view.findViewById(R.id.subTitle);
        TextView mDate = view.findViewById(R.id.date);
        if (messageList.get(position).isMessageRead() == false) {
            mSubTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            mDate.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        mTitle.setText(messageList.get(position).getFrom().getFirstName());
        mSubTitle.setText(messageList.get(position).getContent());

//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = fromUTC(messageList.get(position).getDateTime());
//        mDate.setText(dateFormat.format(date));


        view.setTag(messageList.get(position).getId());
        return view;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Message> filteredMessageList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredMessageList.addAll(messageFilteredList);
            }else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for(Message m : messageFilteredList){

//                            String to="", cc="", bcc="", tags="", attachments="";
//                            for(String con : m.getCc()){
//                                cc += con + ", ";
//                            }
//                            for(String con : m.getBcc()){
//                                bcc += con + ", ";
//                            }
//                            for(String con : m.getTo()){
//                                to += con + ", ";
//                            }
//                            for(Tag tg : m.getTag()){
//                                tags += tg.getName() + ", ";
//                            }
//                            for(Attachment att : m.getAttachments()){
//                                attachments += att.getName() + ", ";
//                            }

                            if (m.getSubject().toLowerCase().contains(filteredPattern)
                                    || m.getContent().toLowerCase().contains(filteredPattern)
//                                    || m.getFrom().toLowerCase().contains(filteredPattern)
//                                    || cc.toLowerCase().contains(filteredPattern)
//                                    || bcc.toLowerCase().contains(filteredPattern)
//                                    || to.toLowerCase().contains(filteredPattern)
//                                    || tags.toLowerCase().contains(filteredPattern)
//                                    || attachments.toLowerCase().contains(filteredPattern)
                                    ) {

                                filteredMessageList.add(m);
                            }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredMessageList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            messageList.clear();
            messageList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
