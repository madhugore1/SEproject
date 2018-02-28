package com.example.madhura.seproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private ArrayList<String> stationList;
    private Spinner stationSpinner1, stationSpinner2;
    private Button btnSingle, btnReturn, btnFinalBook, getTotal;
    private EditText noTicketsInput;
    private TextView total;
    private int fare = 10, amount = 0, tickets = 0;
    private boolean single = true;
    private String source, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        stationList = new ArrayList<String>();
        addStations();

        stationSpinner1 = (Spinner)findViewById(R.id.station_spinner1);
        stationSpinner2 = (Spinner)findViewById(R.id.station_spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stationList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner1.setAdapter(dataAdapter);
        stationSpinner2.setAdapter(dataAdapter);

        btnSingle = (Button)findViewById(R.id.btn_single_journey);
        btnReturn = (Button)findViewById(R.id.btn_return_journey);
        noTicketsInput = (EditText)findViewById(R.id.no_tickets_input);
        getTotal = (Button)findViewById(R.id.get_total);
        total = (TextView)findViewById(R.id.total);
        btnFinalBook = (Button)findViewById(R.id.btn_final_book);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disable return button
                btnReturn.setBackgroundColor(getResources().getColor(R.color.white));
                btnReturn.setTextColor(getResources().getColor(R.color.colorAccent));

                //enable single button
                btnSingle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btnSingle.setTextColor(getResources().getColor(R.color.white));

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single = false;

                //disable single button
                btnSingle.setBackgroundColor(getResources().getColor(R.color.white));
                btnSingle.setTextColor(getResources().getColor(R.color.colorAccent));

                //enable return button
                btnReturn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btnReturn.setTextColor(getResources().getColor(R.color.white));
            }
        });

        getTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketsString = noTicketsInput.getText().toString();
                if(ticketsString.trim().isEmpty()) {
                    Toast.makeText(BookActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
                else {
                    tickets = Integer.valueOf(noTicketsInput.getText().toString());
                }
                if(single) {
                    amount = tickets * fare;
                }
                else {
                    amount = tickets * fare * 2;
                }

                total.setText(String.valueOf(amount));
            }
        });

        btnFinalBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source = stationSpinner1.getSelectedItem().toString();
                destination = stationSpinner2.getSelectedItem().toString();
                if(source.equals(destination) || tickets == 0) {
                    Toast.makeText(BookActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(BookActivity.this, GenerateQRActivity.class));
                }
            }
        });

    }

    public void addStations() {
        stationList.add("CSMT");
        stationList.add("Masjid");
        stationList.add("Sandhurst Road");
        stationList.add("Byculla");
        stationList.add("Chinchpokli");
        stationList.add("Currey Road");
        stationList.add("Parel");
        stationList.add("Dadar");
        stationList.add("Matunga");
        stationList.add("Sion");
        stationList.add("Kurla");
        stationList.add("Vidyavihar");
        stationList.add("Ghatkopar");
        stationList.add("Vikhroli");
        stationList.add("Kanjur Marg");
        stationList.add("Bhandup");
        stationList.add("Nahur");
        stationList.add("Mulund");
        stationList.add("Thane");
    }
}
