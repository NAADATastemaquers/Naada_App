package com.example.naada.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private Button gSignOut;
    private FloatingActionButton mMainFab,mDarkFab,mMusicFab;
    private TextView mDarkFabText,mMusicFabText;
    private Boolean isOpen;
    private Animation mFabOpen,mFabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NavBarSetup();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mMainFab=findViewById(R.id.main_fab);
        mDarkFab=findViewById(R.id.dark_fab);
        mMusicFab=findViewById(R.id.music_fab);
        mDarkFabText=findViewById(R.id.dark_fab_text);
        mMusicFabText=findViewById(R.id.music_fab_text);
        mFabOpen= AnimationUtils.loadAnimation(ProfileActivity.this,R.anim.fab_open);
        mFabClose=AnimationUtils.loadAnimation(ProfileActivity.this,R.anim.fab_close);

        gSignOut=findViewById(R.id.signOut);
        gSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.signOut:
                        signOut();
                        Intent LoginActivity=new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(LoginActivity);
                        finish();
                        break;
                }
            }
        });

        mDarkFab.setVisibility(View.INVISIBLE);
        mMusicFab.setVisibility(View.INVISIBLE);
        mDarkFabText.setVisibility(View.INVISIBLE);
        mMusicFabText.setVisibility(View.INVISIBLE);
        isOpen=false;
        mMainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    mDarkFab.setAnimation(mFabClose);
                    mMusicFab.setAnimation(mFabClose);
                    mDarkFabText.setVisibility(View.INVISIBLE);
                    mMusicFabText.setVisibility(View.INVISIBLE);
                    isOpen=false;
                }else{
                    mDarkFab.setAnimation(mFabOpen);
                    mMusicFab.setAnimation(mFabOpen);
                    mDarkFabText.setVisibility(View.VISIBLE);
                    mMusicFabText.setVisibility(View.VISIBLE);
                    isOpen=true;
                }
            }
        });

        //Darkmode
        mDarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dk=new Intent(ProfileActivity.this, NightMode.class);
                startActivity(dk);
                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

        mMusicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music_activity=new Intent(ProfileActivity.this,MusicPlayerActivity.class);
                startActivity(music_activity);
                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this, "Sign out Succesfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void NavBarSetup() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
        BottomNavHelper.switchActivities(ProfileActivity.this,bottomNavigationView);
    }
}
