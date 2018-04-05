package com.example.madhura.seproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    private Button btnBook, btnHistory, signOut,btnTc;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    Bitmap bitmap ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // for registration and to check with firebase auth
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else
                {
                    if(user.getEmail().toString().equals("gujarshlok@gmail.com")) {
                        btnTc.setVisibility(View.VISIBLE);
                        btnTc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, TcActivity.class));
                            }
                        });
                    }

                }
            }
        };

        btnBook = (Button)findViewById(R.id.btn_book);
        btnHistory = (Button)findViewById(R.id.btn_history);
        signOut = (Button)findViewById(R.id.sign_out);
        btnTc=(Button)findViewById(R.id.btn_tc);

        // various onclick listeners
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookActivity.class));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });



    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    20, 20, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.colorAccent);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 50, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onBackPressed() {

    }
}