package com.example.madhura.seproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harsh on 3/19/2018.
 */

public class HistoryAdapter extends ArrayAdapter<Ticket> {

    public Ticket ticket[];
    int layout;
    Context mContext;

    public HistoryAdapter(Ticket[] ticket, int layout, Context context) {
        super(context, layout);
        this.ticket = ticket;
        this.mContext = context;
        layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
        }

        Ticket newTicket = ticket[position];

        TextView source = (TextView)convertView.findViewById(R.id.source);
    }
}
