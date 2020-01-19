package com.example.naada.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.naada.R;
import com.example.naada.view.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavHelper extends androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior {
    public BottomNavHelper() {
    }

    public BottomNavHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void switchActivities(final Context context, BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottom_nav_home:
                        context.startActivity(new Intent(context, MainActivity.class));
                        ((Activity) context).overridePendingTransition(0, 0);
                        ((Activity) context).finish();
                        break;
//                    case R.id.bottom_nav_residents:
//                        context.startActivity(new Intent(context, hanasuactivity.class));
//                        ((Activity) context).overridePendingTransition(0, 0);
//                        ((Activity) context).finish();
//                        break;
//                    case R.id.bottom_nav_profile:
//                        context.startActivity(new Intent(context, ProfileActivity.class));
//                        ((Activity) context).overridePendingTransition(0, 0);
//                        ((Activity) context).finish();
//                        break;
                }
                return false;
            }
        });
    }
}