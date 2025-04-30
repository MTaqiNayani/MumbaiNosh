package com.example.mumbainosh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestroDashboard extends AppCompatActivity {

    private TextView timingTextView;
    private TextView priceTextView;
    private BottomNavigationView bottomNavigationView;
    private ImageView logoutButton;  // Assuming you add an ImageView for logout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restro_dashboard);

        timingTextView = findViewById(R.id.timingTextView);
        priceTextView = findViewById(R.id.priceTextView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        logoutButton = findViewById(R.id.btnLogout); // The ImageView for logout

        bottomNavigationView.setSelectedItemId(R.id.nav_avail_food);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.nav_avail_food) {
                intent = new Intent(this, AavailFoodList.class);
            } else if (item.getItemId() == R.id.nav_first_screen) {
                intent = new Intent(this, FirstScreen.class);
            } else if (item.getItemId() == R.id.nav_about) {
                intent = new Intent(this, MapsActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish(); // Close current activity
                return true;
            }

            return false;
        });

        // Get extras from the intent
        String timing = getIntent().getStringExtra("TIMING");
        String price = getIntent().getStringExtra("PRICE");

        if (timing != null) {
            timingTextView.setText(timing);
        } else {
            timingTextView.setText("No timing available"); // Default message if timing is null
        }

        if (price != null) {
            priceTextView.setText("At the price of ₹: " + price);
        } else {
            priceTextView.setText("Price not available"); // Default message if price is null
        }

        // Add listener to the logout button
        logoutButton.setOnClickListener(view -> showLogoutDialog());
    }

    // Method to show logout dialog
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Your data has been deleted. Thanks for using our app.")
                .setPositiveButton("OK", (dialog, which) -> {

                    dialog.dismiss();

                    Intent intent = new Intent(RestroDashboard.this, SendOTPActivity.class); // Replace with your login activity
                    startActivity(intent);
                    finish(); // Finish current activity
                })
                .setCancelable(false) // Prevent closing the dialog without pressing "OK"
                .show();
    }
}
