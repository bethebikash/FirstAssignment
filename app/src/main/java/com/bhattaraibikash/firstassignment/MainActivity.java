package com.bhattaraibikash.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etDateIn, etDateOut, etNoOfAdult, etNoOfChild, etNoOfRoom;
    Spinner spRoomType;
    Button btnCalculate;
    TextView tvResult;

    public String roomType[] = {"Deluxe  (Rs.2000)", "Premium  (Rs.4000)", "Presidential  (Rs.5000)"}, result;

    public int noOfRooms, noOfDays, price;
    public double total, vat, grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDateIn = findViewById(R.id.etDateIn);
        etDateOut = findViewById(R.id.etDateOut);
        spRoomType = findViewById(R.id.spRoomType);
        etNoOfAdult = findViewById(R.id.etNoOfAdult);
        etNoOfChild = findViewById(R.id.etNoOfChild);
        etNoOfRoom = findViewById(R.id.etNoOfRoom);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        etDateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker("dateIn");
            }
        });

        etDateOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDateIn.getText().toString().isEmpty()) {
                    etDateIn.setError("Please Choose Check-In Date first!");
                    Toast.makeText(getApplicationContext(), "Please Choose Check-In Date first!!", Toast.LENGTH_SHORT).show();
                } else {
                    loadDatePicker("dateOut");
                }
            }
        });

        ArrayAdapter adapterRoom = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                roomType
        );

        spRoomType.setAdapter(adapterRoom);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(etDateIn.getText())) {
                        etDateIn.setError("Please Choose Check-In Date!");


                    } else if (TextUtils.isEmpty(etDateOut.getText())) {
                        etDateOut.setError("Please Choose Check-Out Date!");
                        Toast.makeText(getApplicationContext(), "Please Choose Check-In Date!", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(spRoomType.getSelectedItem().toString())) {
                        etDateOut.setError("Please Choose Check-Out Date!");
                        Toast.makeText(getApplicationContext(), "Please Choose Check-Out Date!", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(etNoOfAdult.getText())) {
                        etNoOfAdult.setError("Please enter Number of Adult!");

                    } else if (TextUtils.isEmpty(etNoOfChild.getText())) {
                        etNoOfChild.setError("Please enter Number of Child!");

                    } else if (TextUtils.isEmpty(etNoOfRoom.getText())) {
                        etNoOfRoom.setError("Please enter Number of Rooms!");

                    } else {

//                        Calculating Number of Days
                        String dateIn = etDateIn.getText().toString();
                        String dateOut = etDateOut.getText().toString();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
                        Date in = simpleDateFormat.parse(dateIn);
                        Date out = simpleDateFormat.parse(dateOut);
                        long diff = Math.abs(in.getTime() - out.getTime());
                        long diffInDays = diff / (24 * 60 * 60 * 1000);

                        noOfDays = Integer.parseInt(diffInDays + "");
                        noOfRooms = Integer.parseInt(etNoOfRoom.getText() + "");
                        if (spRoomType.getSelectedItem() == "Deluxe  (Rs.2000)") {
                            price = 2000;
                        } else if (spRoomType.getSelectedItem() == "Premium  (Rs.4000)") {
                            price = 4000;
                        } else {
                            price = 5000;
                        }

                        total = noOfDays * noOfRooms * price;
                        vat = 0.13 * total;
                        grandTotal = total + vat;
                        result = "Total : Rs." + total + "\nVAT(13%) : Rs." + vat + "\nGrand Total : Rs." + grandTotal;

                        tvResult.setText(result);
                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });

    }

    private void loadDatePicker(final String btnType) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;

                if (btnType == "dateIn") {
                    etDateIn.setText(date);
                } else {
                    try {
                        String startDate = etDateIn.getText().toString();
                        String endDate = date;

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
                        Date sDate = simpleDateFormat.parse(startDate);
                        Date eDate = simpleDateFormat.parse(endDate);

                        if(eDate.after(sDate)){
                            etDateOut.setText(date);
                        } else {
                            etDateOut.setError("Check-Out Date should be greater than Check-In Date!");
                            Toast.makeText(getApplicationContext(), "Check-Out Date should be greater than Check-In Date!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }

                }
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}
