package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavBarSetup();
    }
    private void NavBarSetup() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavHelper());
        BottomNavHelper.switchActivities(MainActivity.this, bottomNavigationView);
    }

}
