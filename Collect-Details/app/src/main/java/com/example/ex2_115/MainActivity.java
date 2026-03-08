package com.example.ex2_115;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button btnDate, btnSubmit;
    private boolean isDateSelected = false;

    private EditText etName, etAge, etPhone;
    private RadioGroup rgGender;
    private Spinner spinnerDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Views
        text = findViewById(R.id.showText);
        btnDate = findViewById(R.id.button);     // Date button
        btnSubmit = findViewById(R.id.button2);  // Submit button

        etName = findViewById(R.id.editTextText);
        etAge = findViewById(R.id.editTextText4);
        etPhone = findViewById(R.id.editTextText5);

        rgGender = findViewById(R.id.radioGroup);

        spinnerDoctor = findViewById(R.id.spinner2);

        // Date Button
        btnDate.setOnClickListener(view -> openDialog());

        // Submit Button
        btnSubmit.setOnClickListener(view -> validateForm());

        // Insets (only for padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Date Picker
    private void openDialog() {
        Calendar calendar = Calendar.getInstance();
        int yyyy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    text.setText(day + "." + (month + 1) + "." + year);
                    isDateSelected = true;
                }, yyyy, mm, dd);

        dialog.show();
    }

    // Form Validation
    private void validateForm() {

        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Enter name");
            return;
        }

        if (age.isEmpty()) {
            etAge.setError("Enter age");
            return;
        }

        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.length() != 10) {
            etPhone.setError("Enter valid 10-digit phone number");
            return;
        }

        if (spinnerDoctor.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select doctor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isDateSelected) {
            Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Submitted successfully", Toast.LENGTH_LONG).show();
    }
}