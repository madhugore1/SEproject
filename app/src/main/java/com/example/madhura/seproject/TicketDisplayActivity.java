package com.example.madhura.seproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class TicketDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_display);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        ImageView img = (ImageView) findViewById(R.id.ticketDisplay);

        Log.e("TAG",id);

        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Android/data/com.example.madhura.seproject/Files/" + id +".jpg");
        img.setImageBitmap(bitmap);
    }
}
