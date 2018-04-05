package com.example.madhura.seproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TicketDisplayActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_ticket_display);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView userDisp = (TextView) findViewById(R.id.UserDisplay);
        userDisp.setText(user.getEmail().toString());

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        ImageView img = (ImageView) findViewById(R.id.ticketDisplay);

        Log.e("TAG",id);
        //Image retrieval call
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Android/data/com.example.madhura.seproject/Files/" + id +".jpg");
        img.setImageBitmap(bitmap);
    }
}
