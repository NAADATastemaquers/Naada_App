package com.example.naada.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.example.naada.data.adapters.FavSongsAdapter;
import com.example.naada.data.adapters.fav_details;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    GoogleSignInClient mGoogleSignInClient;
    private Button gSignOut;
    private RecyclerView recyclerView ;
    private FavSongsAdapter favSongsAdapter;
    private String userEmail;
    private FloatingActionButton mMainFab,mDarkFab,mMusicFab;
    private TextView mDarkFabText,mMusicFabText;
    private Boolean isOpen;
    private Animation mFabOpen,mFabClose;
    private ImageView profileImage;
    private TextView profileName,profileMail;
    private CardView profileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NavBarSetup();

        profileImage=findViewById(R.id.profile_image);
        profileName=findViewById(R.id.profile_name);
        profileMail=findViewById(R.id.profile_mail);

        profileView=findViewById(R.id.cordinate);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        userEmail = acct.getEmail();
        Log.d(TAG, "setupRecyclerView: "+ userEmail);

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        if(acct!=null){
            String name=acct.getDisplayName();
            String email=acct.getEmail();
            Uri imageUrl=acct.getPhotoUrl();
            profileMail.setText(email);
            profileName.setText(name);
            Glide.with(ProfileActivity.this).load(imageUrl).centerCrop().load(imageUrl).into(profileImage);
        }

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

//        Merge everything from here



        setupRecyclerView();




//        Till here into new branch



        mDarkFab.setVisibility(View.INVISIBLE);
        mMusicFab.setVisibility(View.INVISIBLE);
        mDarkFabText.setVisibility(View.INVISIBLE);
        mMusicFabText.setVisibility(View.INVISIBLE);
        isOpen=false;
        mMainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
//                    mDarkFab.setAnimation(mFabClose);
                    mMusicFab.setAnimation(mFabClose);
//                    mDarkFabText.setVisibility(View.INVISIBLE);
                    mMusicFabText.setVisibility(View.INVISIBLE);
                    isOpen=false;
                }else{
//                    mDarkFab.setAnimation(mFabOpen);
                    mMusicFab.setAnimation(mFabOpen);
//                    mDarkFabText.setVisibility(View.VISIBLE);
                    mMusicFabText.setVisibility(View.VISIBLE);
                    isOpen=true;
                }
            }
        });

        //Darkmode
//        mDarkFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent dk=new Intent(ProfileActivity.this, NightMode.class);
//                startActivity(dk);
//                mDarkFab.setAnimation(mFabClose);
//                mMusicFab.setAnimation(mFabClose);
//                mDarkFabText.setVisibility(View.INVISIBLE);
//                mMusicFabText.setVisibility(View.INVISIBLE);
//                isOpen=false;
//            }
//        });

        mMusicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music_activity=new Intent(ProfileActivity.this,MusicPlayerActivity.class);
                startActivity(music_activity);
//                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
//                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

    }
    //        This too
    private void setupRecyclerView() {
        recyclerView  = findViewById(R.id.favSong_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://fast-garden-54651.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        Favorite favorite = retrofit.create(Favorite.class);

        Call<List<fav_details>> call= favorite.getFavSongs(userEmail);
        call.enqueue(new Callback<List<fav_details>>() {
            @Override
            public void onResponse(Call<List<fav_details>> call, Response<List<fav_details>> response) {

                List<fav_details> fav_details = response.body();
                for(fav_details f : fav_details){
                    Log.d(TAG, "data: "+f.getEmail());
                }

                FavSongsAdapter adapter = new FavSongsAdapter(fav_details,ProfileActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<fav_details>> call, Throwable t) {

            }
        });


    }
    //       This too




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
