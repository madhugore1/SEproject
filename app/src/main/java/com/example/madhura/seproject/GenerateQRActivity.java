package com.example.madhura.seproject;

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

public class GenerateQRActivity extends AppCompatActivity {

    private ImageView qrImage;
    private String qrString;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference, mTicketReference;
    private String TAG = GenerateQRActivity.class.getSimpleName();

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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference("users").child(user.getUid()).child("booked_tickets");

        //get current date and time
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = sdf.format(currentTime);

        qrString = email + dateTime;

        Log.v(TAG, qrString);

        Bitmap qrCode1 = new AwesomeQRCode.Renderer()
                .contents(qrString)
                .size(800).margin(20)
                .render();
        qrImage.setImageBitmap(qrCode1);

        String ticket_id = mUserReference.push().getKey();
        mUserReference.child("ticket_id").setValue(ticket_id);

        Ticket ticket = new Ticket(user.getEmail());
        mTicketReference = mFirebaseDatabase.getReference().child("tickets").child(ticket_id);
        mTicketReference.child("user_id").setValue(user.getEmail());

    }
}
