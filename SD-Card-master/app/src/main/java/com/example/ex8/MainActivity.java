package com.example.ex8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etFileName, etContent;
    private Button btnSave, btnRead;
    private TextView tvStatus;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFileName = findViewById(R.id.etFileName);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        tvStatus = findViewById(R.id.tvStatus);

        checkStoragePermission();

        btnSave.setOnClickListener(v -> saveNoteToSDCard());
        btnRead.setOnClickListener(v -> readNoteFromSDCard());
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            tvStatus.setText("Status: Android 13+ detected. Using scoped storage.");
            return;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_CODE);
        } else {
            tvStatus.setText("Status: Storage permission already granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,
                        "Permission Granted! You can now save notes.",
                        Toast.LENGTH_SHORT).show();
                tvStatus.setText("Status: Permission granted");
            } else {
                Toast.makeText(this,
                        "Permission Denied! Cannot write to SD card.",
                        Toast.LENGTH_LONG).show();
                tvStatus.setText("Status: Permission denied");
            }
        }
    }

    private void saveNoteToSDCard() {
        String fileName = etFileName.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please enter a file name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (content.isEmpty()) {
            Toast.makeText(this, "Note content cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isExternalStorageAvailable()) {
            tvStatus.setText("Status: SD Card not available");
            Toast.makeText(this, "SD Card is not mounted!", Toast.LENGTH_LONG).show();
            return;
        }

        File sdCardDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "SDCardNotes"
        );

        if (!sdCardDir.exists()) {
            boolean created = sdCardDir.mkdirs();
            if (!created) {
                tvStatus.setText("Status: Failed to create directory");
                return;
            }
        }

        File noteFile = new File(sdCardDir, fileName + ".txt");

        try {
            FileWriter writer = new FileWriter(noteFile);
            writer.write(content);
            writer.flush();
            writer.close();

            String path = noteFile.getAbsolutePath();
            tvStatus.setText("Status: Note saved\nPath: " + path);
            Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            tvStatus.setText("Status: Error saving note\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void readNoteFromSDCard() {
        String fileName = etFileName.getText().toString().trim();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Enter the file name to read!", Toast.LENGTH_SHORT).show();
            return;
        }

        File sdCardDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "SDCardNotes"
        );

        File noteFile = new File(sdCardDir, fileName + ".txt");

        if (!noteFile.exists()) {
            tvStatus.setText("Status: File not found\nLooked at: " + noteFile.getAbsolutePath());
            Toast.makeText(this, "File does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(noteFile));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            etContent.setText(sb.toString().trim());
            tvStatus.setText("Status: Note loaded\nFrom: " + noteFile.getAbsolutePath());

        } catch (IOException e) {
            tvStatus.setText("Status: Error reading file\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}