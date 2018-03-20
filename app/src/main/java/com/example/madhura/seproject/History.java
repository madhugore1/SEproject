package com.example.madhura.seproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class History extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference, mTicketReference;
    public ArrayList<String> ticket_ids = new ArrayList<String>();
    public ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    public ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = (ListView) findViewById(R.id.history_list);

        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //get email of current user
        String email = user.getUid();

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

        Log.v("History", "Test Test Test");
        HistoryAdapter historyAdapter = new HistoryAdapter(this, R.layout.history_list_container, tickets);
        historyList.setAdapter(historyAdapter);

    }

    public void getTicketDetails(String ticket_id) {
        mTicketReference.child(ticket_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ticketSnapshot) {
                Ticket ticket_details = ticketSnapshot.getValue(Ticket.class);
                tickets.add(ticket_details);
                //Log.v("History ", "ticket_details : source : " + ticket_details.getSource());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
