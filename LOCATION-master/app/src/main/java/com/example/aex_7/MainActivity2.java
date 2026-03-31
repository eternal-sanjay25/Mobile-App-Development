package com.example.aex_7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aex_7.R;

public class MainActivity2 extends AppCompatActivity {

    TextView tvLatitude, tvLongitude, tvLink;
    Button btnShare, btnOpenMap;

    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Connect views
        tvLatitude  = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvLink      = findViewById(R.id.tvLink);
        btnShare    = findViewById(R.id.btnShare);
        btnOpenMap  = findViewById(R.id.btnOpenMap);

        // Unpack data from MainActivity
        Intent intent = getIntent();
        latitude  = intent.getDoubleExtra("latitude", 0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);

        // Display coordinates
        tvLatitude.setText("Latitude: "  + latitude);
        tvLongitude.setText("Longitude: " + longitude);

        // ✅ Build Google Maps link
        String mapsLink = "https://maps.google.com/?q=" + latitude + "," + longitude;
        tvLink.setText(mapsLink);

        // ✅ Share button — opens WhatsApp, SMS, Gmail etc.
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Current Location");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Here is my current location:\n" + mapsLink);

            // Opens share sheet (WhatsApp, SMS, Gmail, etc.)
            startActivity(Intent.createChooser(shareIntent, "Share Location via"));
        });

        // ✅ Open Map button — opens Google Maps directly
        btnOpenMap.setOnClickListener(v -> {
            Uri mapUri = Uri.parse(mapsLink);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps"); // target Google Maps

            // If Google Maps not installed, fallback to browser
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Open in browser instead
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mapsLink)));
            }
        });
    }
}