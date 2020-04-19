package com.example.naada.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView name,email,id;
    GoogleSignInClient mGoogleSignInClient;
    ImageView playButton;
    ImageView album_image;

    private BottomNavigationView bottomNavigationView;
    private static final String KEY_IMAGE="image";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference contentRef= db.collection("songs").document("song");
    private FloatingActionButton mMainFab,mDarkFab,mMusicFab;
    private TextView mDarkFabText,mMusicFabText;
    private Boolean isOpen;
    private Animation mFabOpen,mFabClose;
    private Switch toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected())
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning)
                    .setTitle("Internet connection Alert!")
                    .setMessage("Please check your internet connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
        setContentView(R.layout.activity_main);
        playButton=findViewById(R.id.play_button);
        album_image=findViewById(R.id.imageAlbum);

        mMainFab=findViewById(R.id.main_fab);
        mDarkFab=findViewById(R.id.dark_fab);
        mMusicFab=findViewById(R.id.music_fab);
        mDarkFabText=findViewById(R.id.dark_fab_text);
        mMusicFabText=findViewById(R.id.music_fab_text);
        mFabOpen=AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_open);
        mFabClose=AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_close);

        NavBarSetup();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                if(documentSnapshot.exists()){
                    String album_image_url = documentSnapshot.getString(KEY_IMAGE);
                    Glide.with(MainActivity.this).load(album_image_url).centerCrop().load(album_image_url).into(album_image);
                }else{
                    Toast.makeText(MainActivity.this,"​Document doesn't exists​",Toast.LENGTH_SHORT).show();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music_activity=new Intent(MainActivity.this,MusicPlayerActivity.class);
                startActivity(music_activity);
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

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
        mDarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent dk=new Intent(MainActivity.this, NightMode.class);
//                startActivity(dk);
//                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
//                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

       mMusicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music_activity=new Intent(MainActivity.this,MusicPlayerActivity.class);
                startActivity(music_activity);
//                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
//                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }

    private void NavBarSetup() {
        bottomNavigationView = findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavHelper());
        BottomNavHelper.switchActivities(MainActivity.this,bottomNavigationView);
    }
}
