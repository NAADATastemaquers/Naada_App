package com.example.naada.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SlidingDrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    //private final int SPLASH_DISPLAY_LENGTH = 30000;
    //Button slideButton,b1, b2,b3,b4;
    SlidingDrawer slidingDrawer;

    private BottomNavigationView bottomNavigationView;


    @Deprecated
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavBarSetup();

        //slideButton = (Button) findViewById(R.id.slideButton);
        slidingDrawer = findViewById(R.id.slidingDrawer);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                // slideButton.setBackgroundResource(R.drawable.down_arrow_icon);
                slidingDrawer.setBackgroundResource(R.color.white);
            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {

                //  slideButton.setBackgroundResource(R.drawable.upwar_arrow_icon);
                slidingDrawer.setBackgroundColor(Color.TRANSPARENT);
            }
        });


    }
    private void NavBarSetup() {
        bottomNavigationView = findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavHelper());
        BottomNavHelper.switchActivities(MainActivity.this,bottomNavigationView);
    }

}
