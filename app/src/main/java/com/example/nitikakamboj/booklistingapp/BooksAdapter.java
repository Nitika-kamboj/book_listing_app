package com.example.nitikakamboj.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Nitika Kamboj on 04-07-2017.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

public BooksAdapter(Context context, List<Books> books)
{
super(context,0,books);
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
     View listItemView =convertView;
     if(listItemView==null)
     {
     listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
     }

     Books currentBooks=getItem(position);

     TextView titleView=(TextView) listItemView.findViewById(R.id.title);
     titleView.setText(currentBooks.getmTitle());

     TextView authorView=(TextView) listItemView.findViewById(R.id.authors);
     authorView.setText(currentBooks.getmAuthor());

    return listItemView;
    }
}
