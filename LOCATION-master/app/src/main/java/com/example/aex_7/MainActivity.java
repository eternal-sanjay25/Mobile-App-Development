package com.example.aex_7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 100;
    Button btnGetLocation;

    // FusedLocationProviderClient — Google's location engine
    FusedLocationProviderClient fusedClient;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize fused client
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(v -> checkPermissionAndGetLocation());
    }

    private void checkPermissionAndGetLocation() {
        boolean fineGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        boolean coarseGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (!fineGranted && !coarseGranted) {
            // Ask permission
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST);
        } else {
            fetchLiveLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            boolean anyGranted = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    anyGranted = true;
                    break;
                }
            }

            if (anyGranted) {
                fetchLiveLocation();
            } else {
                Toast.makeText(this,
                        "Go to App Settings → Permissions → Location → Allow",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetchLiveLocation() {
        Toast.makeText(this, "Getting your live location...", Toast.LENGTH_SHORT).show();

        // Step 1: Build a LocationRequest
        // This tells fused client HOW to fetch location
        LocationRequest locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,  // Use GPS for max accuracy
                1000)                              // Request update every 1 second
                .setMinUpdateIntervalMillis(500)   // Fastest = every 500ms
                .setMaxUpdates(1)                  // ✅ We only need ONE fresh fix
                .build();

        // Step 2: Build a LocationCallback
        // This fires when a fresh location arrives
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // Stop updates immediately — we got what we need
                fusedClient.removeLocationUpdates(locationCallback);

                if (locationResult.getLastLocation() != null) {
                    double latitude  = locationResult.getLastLocation().getLatitude();
                    double longitude = locationResult.getLastLocation().getLongitude();

                    // Send to Screen 2
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this,
                            "Could not get location. Try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Step 3: Check permission one more time (Android lint requires this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Step 4: Start requesting location
        fusedClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                getMainLooper()  // Run callback on main/UI thread
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up — stop location updates if activity is destroyed
        if (fusedClient != null && locationCallback != null) {
            fusedClient.removeLocationUpdates(locationCallback);
        }
    }
}
