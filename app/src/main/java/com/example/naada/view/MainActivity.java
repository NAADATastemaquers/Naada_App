package com.example.naada.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.example.naada.util.NightMode;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    SlidingDrawer slidingDrawer;
    private BottomNavigationView bottomNavigationView;
    private Button Theme;
    private static final String KEY_IMAGE="image";
    ImageView arrow;
    //LottieAnimationView GoToPlayer;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference contentRef= db.collection("songs").document("song");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrow=findViewById(R.id.arrow);
        playButton=findViewById(R.id.play_button);
        album_image=findViewById(R.id.imageAlbum);

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

        contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
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
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            email.setText(personEmail);
            name.setText(personName);
            id.setText(personId);
        }

//        GoToPlayer=findViewById(R.id.play);
//        GoToPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent music=new Intent(MainActivity.this,MusicPlayerActivity.class);
//                startActivity(music);
//                finish();
//            }
//        });





        //Darkmode
        Theme=findViewById(R.id.theme);
        Theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dk=new Intent(MainActivity.this, NightMode.class);
                startActivity(dk);
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
