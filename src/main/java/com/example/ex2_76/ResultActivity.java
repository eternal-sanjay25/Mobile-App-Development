package com.example.ex2_76;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv = findViewById(R.id.textViewResult);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String name = bundle.getString("name");
            String age = bundle.getString("age");
            String phone = bundle.getString("phone");
            String date = bundle.getString("date");
            String doctor = bundle.getString("doctor");
            String gender = bundle.getString("gender");
            String time = bundle.getString("time");

            String result =
                    name + "\n" +
                    age + "\n" +
                    phone + "\n" +
                    gender + "\n" +
                    doctor + "\n" +
                    date + "\n" +
                    time;

            tv.setText(result);
        }
    }
}
