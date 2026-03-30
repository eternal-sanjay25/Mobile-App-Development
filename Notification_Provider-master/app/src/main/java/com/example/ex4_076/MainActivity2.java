package com.example.ex4_076;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText e1, e2;
    Button b, b2, b3, b4, b5;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Insets (keep only UI padding here)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Initialize Views (CORRECT PLACE)
        b = findViewById(R.id.button2);
        b2 = findViewById(R.id.button4);
        b3 = findViewById(R.id.button5);
        b4 = findViewById(R.id.button6);
        b5 = findViewById(R.id.button7);

        e1 = findViewById(R.id.editTextText3);
        e2 = findViewById(R.id.editTextText4);

        // ✅ Database
        db = openOrCreateDatabase("Student", MODE_PRIVATE, null);
        c = db.rawQuery("SELECT * FROM Student", null);

        // FIRST RECORD
        b.setOnClickListener(v -> {
            if (c.moveToFirst()) {
                e1.setText(c.getString(0));
                e2.setText(c.getString(1));
            }
        });

        // LAST RECORD
        b2.setOnClickListener(v -> {
            if (c.isLast()) {
                Toast.makeText(MainActivity2.this, "This is Last", Toast.LENGTH_SHORT).show();
            } else {
                c.moveToLast();
                e1.setText(c.getString(0));
                e2.setText(c.getString(1));
            }
        });

        // NEXT RECORD
        b3.setOnClickListener(v -> {
            if (c.moveToNext()) {
                e1.setText(c.getString(0));
                e2.setText(c.getString(1));
            } else {
                Toast.makeText(MainActivity2.this, "No More Records", Toast.LENGTH_SHORT).show();
            }
        });

        // PREVIOUS RECORD
        b4.setOnClickListener(v -> {
            if (c.isFirst()) {
                Toast.makeText(MainActivity2.this, "This is First", Toast.LENGTH_SHORT).show();
            } else if (c.moveToPrevious()) {
                e1.setText(c.getString(0));
                e2.setText(c.getString(1));
            }
        });

        // 🔥 BUTTON WITH ALERT DIALOG
        b5.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

            builder.setMessage("Do you want to go back?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent i = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(i);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.setTitle("Alert Message");
            alert.show();
        });
    }

    // 🔥 BACK BUTTON ALERT
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

        builder.setMessage("Close the Application?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}