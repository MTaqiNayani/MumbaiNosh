package com.example.mumbainosh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }

    public void onProviderClick(View view) {
        Intent intent = new Intent(FirstScreen.this, SendOTPActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean isOtpVerifiedForProvider = sharedPreferences.getBoolean("is_provider_otp_verified", true);

        if (isOtpVerifiedForProvider) {
            intent = new Intent(FirstScreen.this, ProviderScreen.class);
            startActivity(intent);
        } else {
            startActivity(intent);
        }
    }

    public void onUserClick(View view) {
        Intent intent = new Intent(FirstScreen.this, MapsActivity.class);
        startActivity(intent);
    }
}