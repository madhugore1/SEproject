package com.example.madhura.seproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

    ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    public HistoryAdapter(Context context, int layoutResourceId, ArrayList<Ticket> tickets) {
        super(context, layoutResourceId, tickets);
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.history_list_container, null);

        Log.v("HistoryAdapter ", "HistoryAdapter was called");
        //ticket id
        TextView ticketIdTextView = (TextView) v.findViewById(R.id.ticket_id);
        ticketIdTextView.setText(tickets.get(position).getTicket_id());
        Log.v("HistoryAdapter", ticketIdTextView.getText().toString());
        //source
        TextView sourceTextView = (TextView) v.findViewById(R.id.source);
        sourceTextView.setText(tickets.get(position).getSource());
        //destination
        TextView destTextView = (TextView) v.findViewById(R.id.destination);
        destTextView.setText(tickets.get(position).getDestination());
        //no. of tickets
        TextView numberOfTicketsTextView = (TextView) v.findViewById(R.id.number_of_tickets);
        numberOfTicketsTextView.setText(tickets.get(position).getTickets());
        //total amount
        TextView totalAmountTextView = (TextView) v.findViewById(R.id.total_amount);
        totalAmountTextView.setText(tickets.get(position).getAmount());
        //timestamp
        TextView dateTimeTextView = (TextView) v.findViewById(R.id.date_time);
        dateTimeTextView.setText(tickets.get(position).getTimestamp());

        return v;

    }
}
