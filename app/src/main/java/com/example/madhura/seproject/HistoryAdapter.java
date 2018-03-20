package com.example.madhura.seproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Harsh on 3/19/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<Ticket> tickets;
    private final Activity activity;

    public HistoryAdapter(List<Ticket> tickets, Activity activity) {
        this.activity = activity;
        this.tickets = tickets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.history_list_container,parent,false));
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        holder.ticketIdTextView.setText(tickets.get(position).getTicket_id());
        holder.sourceTextView.setText(tickets.get(position).getSource());
        holder.destTextView.setText(tickets.get(position).getDestination());
        holder.numberOfTicketsTextView.setText(String.valueOf(tickets.get(position).getTickets()));
        holder.totalAmountTextView.setText(String.valueOf(tickets.get(position).getAmount()));
        holder.dateTimeTextView.setText(tickets.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView ticketIdTextView,sourceTextView,numberOfTicketsTextView,totalAmountTextView,dateTimeTextView,destTextView;
        ViewHolder(View itemView) {
            super(itemView);
            ticketIdTextView = (TextView) itemView.findViewById(R.id.ticket_id);
            sourceTextView = (TextView) itemView.findViewById(R.id.source);
            destTextView = (TextView) itemView.findViewById(R.id.destination);
            numberOfTicketsTextView = (TextView) itemView.findViewById(R.id.number_of_tickets);
            totalAmountTextView = (TextView) itemView.findViewById(R.id.total_amount);
            dateTimeTextView = (TextView) itemView.findViewById(R.id.date_time);
        }
    }
}
