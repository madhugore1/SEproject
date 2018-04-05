package com.example.madhura.seproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.github.sumimakito.awesomeqr.AwesomeQRCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrImage = (ImageView)findViewById(R.id.qrimage);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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

        // to save image
        Bitmap qrCode1 = new AwesomeQRCode.Renderer()
                .contents(ticket_id)
                .size(800).margin(20)
                .render();
        qrImage.setImageBitmap(qrCode1);
        savebitmap(ticket_id,qrCode1);
//        uploadImage(ticket_id,qrCode1);

        mUserReference.push().child("ticket_id").setValue(ticket_id);

        Ticket ticket = new Ticket(ticket_id,user.getEmail(),source,destination,amount,tickets,fare,dateTime);
        mTicketReference = mFirebaseDatabase.getReference().child("tickets").child(ticket_id);
        mTicketReference.setValue(ticket);

    }

    // method to store in bitmap
    private void savebitmap(String filename, Bitmap image) {
        File pictureFile = getOutputMediaFile(filename);
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private  File getOutputMediaFile(String filename){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + filename +".jpg");
        return mediaFile;
    }

    private void uploadImage(String ticket_id, Bitmap qrcode) {

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Uploading...");
//        progressDialog.show();
//
//        StorageReference ref = storageReference.child("tickets").child(ticket_id);
//        ref.putFile()
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialog.dismiss();
//                        Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                .getTotalByteCount());
//                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                    }
//                });
    }
}
