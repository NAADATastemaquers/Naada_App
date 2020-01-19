package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResidentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residents);
    }
    private void NavBarSetup() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
        BottomNavHelper.switchActivities(ResidentsActivity.this,bottomNavigationView);
    }
}
