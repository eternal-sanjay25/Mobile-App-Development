package com.example.ex1_115;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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

    // Date
    private TextView text;
    private Button button, b2;
    private boolean isDateSelected = false;

    // Other fields
    private EditText etName, etAge, etPhone;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Spinner spinnerDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Date views
        text = findViewById(R.id.showText);
        button = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);

        // Input fields
        etName = findViewById(R.id.editTextText);
        etAge = findViewById(R.id.editTextText4);
        etPhone = findViewById(R.id.editTextText5);

        rbMale = findViewById(R.id.radioButton7);
        rbFemale = findViewById(R.id.radioButton8);
        rgGender = (RadioGroup) rbMale.getParent();

        spinnerDoctor = findViewById(R.id.spinner2);

        // Pick date
        button.setOnClickListener(view -> openDialog());

        // Submit
        b2.setOnClickListener(view -> validateForm());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Date picker
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

    // Validation
    private void validateForm() {

        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String date = text.getText().toString();
        String doctor = spinnerDoctor.getSelectedItem().toString();
        int selectedGenderId = rgGender.getCheckedRadioButtonId();

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

        if (phone.isEmpty() || phone.length() != 10) {
            etPhone.setError("Enter valid phone number");
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
        Intent i=new Intent(getApplicationContext(),MainActivity2.class);
        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();
        Bundle b=new Bundle();
        b.putString("name", name);
        b.putString("age", age);
        b.putString("phone", phone);
        b.putString("gender", gender);
        b.putString("doctor", doctor);
        b.putString("date", date);
        i.putExtras(b);
        startActivity(i);

    }
}
