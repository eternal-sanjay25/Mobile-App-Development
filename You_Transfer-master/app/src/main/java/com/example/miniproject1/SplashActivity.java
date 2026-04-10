package com.example.miniproject1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvLogoText = findViewById(R.id.tvLogoText);

        // Define the colors for "You" and "Transfer"
        String text = "You Transfer";
        SpannableString spannableString = new SpannableString(text);
        
        // "You" in White
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        // "Transfer" in Orange
        int orangeColor = Color.parseColor("#F59E0B"); // Premium orange
        spannableString.setSpan(new ForegroundColorSpan(orangeColor), 4, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        tvLogoText.setText(spannableString);

        // Start animation
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        tvLogoText.startAnimation(zoomIn);

        // Transition to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
