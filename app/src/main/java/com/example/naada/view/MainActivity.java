package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SlidingDrawer;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView name,email,id;
    GoogleSignInClient mGoogleSignInClient;

    SlidingDrawer slidingDrawer;
    private BottomNavigationView bottomNavigationView;
    private Switch aSwitch;


    Button GoToPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavBarSetup();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        email=findViewById(R.id.emailId);
        name=findViewById(R.id.gname);
        id=findViewById(R.id.gid);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            email.setText(personEmail);
            name.setText(personName);
            id.setText(personId);
        }

        GoToPlayer= findViewById(R.id.player);
        GoToPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MusicPlayerActivity.class);
                startActivity(intent);
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
