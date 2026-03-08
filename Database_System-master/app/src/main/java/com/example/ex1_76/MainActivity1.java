package com.example.ex1_76;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button b, b2;
    EditText e1, e2;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Only padding logic here
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        b = findViewById(R.id.button);
        b2 = findViewById(R.id.button3);   // Make sure button2 exists in XML
        e1 = findViewById(R.id.editTextText);
        e2 = findViewById(R.id.editTextText2);


        // Open or Create Database
        db = openOrCreateDatabase("Student", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Student(Username TEXT, Password TEXT)");

        // Insert Button
        b.setOnClickListener(view -> {

            String user = e1.getText().toString();
            String pass = e2.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            db.execSQL("INSERT INTO Student VALUES('" + user + "','" + pass + "')");

            Toast.makeText(MainActivity.this, "Record Inserted Successfully", Toast.LENGTH_SHORT).show();

            e1.setText("");
            e2.setText("");
        });

        // Next Activity Button
        b2.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(i);
        });
    }
}