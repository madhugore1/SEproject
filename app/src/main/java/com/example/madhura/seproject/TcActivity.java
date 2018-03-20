package com.example.madhura.seproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TcActivity extends AppCompatActivity {

    TextView ticket_id,source,destination,number,date,amount;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTicketReference;
    Button buttonScan;

    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc);

        ticket_id=(TextView)findViewById(R.id.ticket_id);
        source=(TextView)findViewById(R.id.source);
        destination=(TextView)findViewById(R.id.destination);
        number=(TextView)findViewById(R.id.number_of_tickets);
        date=(TextView)findViewById(R.id.date_time);
        amount=(TextView)findViewById(R.id.total_amount);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        qrScan = new IntentIntegrator(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTicketReference = mFirebaseDatabase.getReference("tickets");
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                //converting the data to json
                final String item = result.getContents();
                //setting values to textviews
                //textViewName.setText(obj.getString("name"));
                //textViewAddress.setText(obj.getString("address"));
                Log.d("Tag", String.valueOf(item));

                mTicketReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Ticket ticket_details = dataSnapshot.getValue(Ticket.class);
                        Log.d("ticket", String.valueOf(ticket_details));
                        if(dataSnapshot.child("ticket_id").getValue().toString().equals(item))
                        {
                            Log.d("gg","hurrah");
                            ticket_id.setText(dataSnapshot.child("ticket_id").getValue().toString());
                            source.setText(dataSnapshot.child("source").getValue().toString());
                            destination.setText(dataSnapshot.child("destination").getValue().toString());
                            number.setText(dataSnapshot.child("tickets").getValue().toString());
                            amount.setText(dataSnapshot.child("amount").getValue().toString());
                            date.setText(dataSnapshot.child("timestamp").getValue().toString());

                        }
                        //Log.d("h","hello");
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
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

