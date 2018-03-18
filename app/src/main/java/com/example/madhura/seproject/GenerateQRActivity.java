package com.example.madhura.seproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.github.sumimakito.awesomeqr.AwesomeQRCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GenerateQRActivity extends AppCompatActivity {

    private ImageView qrImage;
    private String qrString;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference, mTicketReference;
    private String TAG = GenerateQRActivity.class.getSimpleName();
    private int fare,amount,tickets;
    private String source,destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrImage = (ImageView)findViewById(R.id.qrimage);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //get email of current user
        String email = user.getEmail();

        Intent intent = getIntent();

        HashMap<String,String> TicketDetails = (HashMap<String,String>) intent.getSerializableExtra("TicketDetails");
        source = TicketDetails.get("source");
        destination = TicketDetails.get("destination");
        fare = Integer.parseInt(TicketDetails.get("fare"));
        amount = Integer.parseInt(TicketDetails.get("amount"));
        tickets = Integer.parseInt(TicketDetails.get("tickets"));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference("users").child(user.getUid()).child("booked_tickets");

        //get current date and time
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = sdf.format(currentTime);

        String ticket_id = mUserReference.push().getKey();

        Bitmap qrCode1 = new AwesomeQRCode.Renderer()
                .contents(ticket_id)
                .size(800).margin(20)
                .render();
        qrImage.setImageBitmap(qrCode1);

        mUserReference.child("ticket_id").push().setValue(ticket_id);

        Ticket ticket = new Ticket(ticket_id,user.getEmail(),source,destination,amount,tickets,fare,dateTime);
        mTicketReference = mFirebaseDatabase.getReference().child("tickets").child(ticket_id);
        mTicketReference.setValue(ticket);

    }
}
