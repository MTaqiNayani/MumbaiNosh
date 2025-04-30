package com.example.mumbainosh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AavailFoodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    private DatabaseReference databaseReference;
    private List<Provider> providerList;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aavail_food_list);
        String userPostalCode = getIntent().getStringExtra("userPostalCode");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        providerList = new ArrayList<>();
        adapter = new ProviderAdapter(providerList);
        recyclerView.setAdapter(adapter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodProviders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providerList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Provider provider = snapshot.getValue(Provider.class);
                    if (provider != null) {
                        providerList.add(provider);
                    }
                }
                if (userPostalCode != null) {
                    sortProvidersByPostalCode(userPostalCode);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving data", databaseError.toException());
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.nav_avail_food) {
                return true; // Return true to indicate that the event was handled
            } else if (item.getItemId() == R.id.nav_first_screen) {
                intent = new Intent(this, FirstScreen.class);
            } else if (item.getItemId() == R.id.nav_about) {
                intent = new Intent(this, MapsActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish(); // Close current activity to prevent stacking
                return true; // Return true to indicate that the event was handled
            }

            return false; // Return false for unhandled cases
        });

        // Set the initial selected item
        bottomNavigationView.setSelectedItemId(R.id.nav_avail_food);
    }

    private void sortProvidersByPostalCode(String userPostalCode) {
        int userPostal = Integer.parseInt(userPostalCode);

        providerList.sort((provider1, provider2) -> {
            int postalCode1 = Integer.parseInt(provider1.getPostalCode());
            int postalCode2 = Integer.parseInt(provider2.getPostalCode());

            int distance1 = Math.abs(userPostal - postalCode1);
            int distance2 = Math.abs(userPostal - postalCode2);

            return Integer.compare(distance1, distance2);
        });
    }
}
