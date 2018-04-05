package com.example.madhura.seproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference, mTicketReference;
    public ArrayList<String> ticket_ids = new ArrayList<String>();
    public ArrayList<Ticket> tickets;
    public RecyclerView historyList;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = (RecyclerView) findViewById(R.id.history_list);

        tickets = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //get email of current user
        String email = user.getUid();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historyAdapter = new HistoryAdapter(tickets,History.this);
        historyList.setAdapter(historyAdapter);
        historyList.setLayoutManager(linearLayoutManager);

        historyAdapter.setOnItemClickListener(new HistoryAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String id = tickets.get(position).getTicket_id();
                Intent intent = new Intent(History.this,TicketDisplayActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTicketReference = mFirebaseDatabase.getReference("tickets");
        mUserReference = mFirebaseDatabase.getReference("users").child(user.getUid()).child("booked_tickets");

        mUserReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ticket_id = snapshot.getValue(String.class);
                    ticket_ids.add(ticket_id);
                    Log.v("History ", "ticket_id : " + ticket_id);
                    getTicketDetails(ticket_id);
                }
                Log.v("History", "Test Test Test");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getTicketDetails(String ticket_id) {
        mTicketReference.child(ticket_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ticketSnapshot) {
                Ticket ticket_details = ticketSnapshot.getValue(Ticket.class);
//                tickets.add(ticket_details);
                Log.v("History ", "ticket_details : source : " + ticket_details.getSource());
                tickets.add(ticket_details);
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
