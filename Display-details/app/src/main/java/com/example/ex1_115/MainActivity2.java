package com.example.ex1_115;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvResult = findViewById(R.id.tvResult);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String name = bundle.getString("name");
            String age = bundle.getString("age");
            String phone = bundle.getString("phone");
            String gender = bundle.getString("gender");
            String doctor = bundle.getString("doctor");
            String date = bundle.getString("date");

            String displayText =
                    "Name: " + name + "\n\n" +
                            "Age: " + age + "\n\n" +
                            "Gender: " + gender + "\n\n" +
                            "Phone: " + phone + "\n\n" +
                            "Doctor: " + doctor + "\n\n" +
                            "Appointment Date: " + date;

            tvResult.setText(displayText);
        }
    }
}
