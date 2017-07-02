package com.example.abhus.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ${AAKASH} on 6/30/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.book_activity,parent,false);
        }
        Book currentData= getItem(position);
        TextView titleView=(TextView)listItemView.findViewById(R.id.title_view);
        titleView.setText(currentData.getmTitle());
        TextView publisherView=(TextView)listItemView.findViewById(R.id.publication_view);
        publisherView.setText(currentData.getmPublisher());

        return listItemView;
    }
}
