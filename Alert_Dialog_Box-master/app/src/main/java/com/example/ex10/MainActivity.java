package com.example.ex10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2, e3;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Insets (ONLY UI padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Initialize Views (IMPORTANT)
        e1 = findViewById(R.id.editTextText4);
        e2 = findViewById(R.id.editTextText6);
        e3 = findViewById(R.id.editTextText7);
        b = findViewById(R.id.button2);

        // ✅ Button Click
        b.setOnClickListener(v -> {

            String to = e1.getText().toString();
            String subject = e2.getText().toString();
            String message = e3.getText().toString();

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            emailIntent.setType("message/rfc822"); // best for email apps

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                Log.i("Email", "Finished sending email...");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "No email app installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}