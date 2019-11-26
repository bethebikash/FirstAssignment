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

    public String roomType[] = {"Deluxe", "Presidential", "Premium"};

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

    }

    private void loadDatePicker(final String btnType) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = "Month/Day/Year: " + month + "/" + dayOfMonth + "/" + year;

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
