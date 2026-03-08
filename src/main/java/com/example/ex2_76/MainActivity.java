package com.example.ex2_76;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2, e3, e4;
    Spinner s1, s2;
    RadioGroup radioGroupGender;
    Button b;

    String name, age, phone, date, doctor, gender, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editTextText);
        e2 = findViewById(R.id.editTextText2);
        e3 = findViewById(R.id.editTextPhone);
        e4 = findViewById(R.id.editTextDate);

        s1 = findViewById(R.id.spinner2);
        s2 = findViewById(R.id.spinner4);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        b = findViewById(R.id.button9);

        // Date Picker
        e4.setOnClickListener(view -> {

            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(
                    MainActivity.this,
                    (view1, y, m, d) -> e4.setText(d + "-" + (m+1) + "-" + y),
                    year, month, day);

            dp.show();
        });

        // Submit Button
        b.setOnClickListener(view -> {

            name = e1.getText().toString();
            age = e2.getText().toString();
            phone = e3.getText().toString();
            date = e4.getText().toString();

            doctor = s1.getSelectedItem().toString();
            time = s2.getSelectedItem().toString();

            int selectedId = radioGroupGender.getCheckedRadioButtonId();

            if(selectedId != -1)
            {
                RadioButton rb = findViewById(selectedId);
                gender = rb.getText().toString();
            }
            else
            {
                gender = "Not Selected";
            }

            // Intent
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);

            // Bundle
            Bundle bundle = new Bundle();

            bundle.putString("name", name);
            bundle.putString("age", age);
            bundle.putString("phone", phone);
            bundle.putString("date", date);
            bundle.putString("doctor", doctor);
            bundle.putString("gender", gender);
            bundle.putString("time", time);

            intent.putExtras(bundle);

            startActivity(intent);

        });

    }
}
