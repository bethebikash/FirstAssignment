package com.bhattaraibikash.firstassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etDateIn, etDateOut, etNoOfAdult, etNoOfChild, etNoOfRoom;
    Spinner spRoomType;
    Button btnCalculate;
    TextView tvResult;

    public String roomType[] = {"Deluxe", "Presidential", "Premium"}, result;

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
                if (etDateIn.getText().toString().isEmpty()){
                    etDateIn.setError("Please Choose Check-In Date!");
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
//              no of days
                noOfDays = 5;
                noOfRooms = Integer.parseInt(etNoOfRoom.getText()+"");
                if(spRoomType.getSelectedItem() == "Deluxe"){
                    price = 2000;
                } else if(spRoomType.getSelectedItem() == "Premium"){
                    price = 4000;
                } else {
                    price = 5000;
                }

                total = noOfDays * noOfRooms * price;
                vat = 0.13 * total;
                grandTotal = total+vat;
                result = "Total : Rs."+total+"\nVAT(13%) : Rs."+vat+"\nGrand Total : Rs."+grandTotal;

                tvResult.setText(result);
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
                String date = "Month/Day/Year: " + (month+1) + "/" + dayOfMonth + "/" + year;

                if (btnType == "dateIn") {
                    etDateIn.setText(date);
                } else {
                    etDateOut.setText(date);
                }
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}
