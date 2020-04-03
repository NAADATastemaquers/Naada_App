package com.example.naada.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.naada.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class  LoginActivity extends AppCompatActivity {

    private TextView signUpBtn;
    private TextView musicBtn;
    private Button googleSignin;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    Button Facebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //GoogleSign in

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        signUpBtn=findViewById(R.id.signUptxt);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUppage=new Intent(LoginActivity.this,SignUp.class);
                startActivity(signUppage);
            }
        });


        musicBtn=findViewById(R.id.gotomusic);
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music=new Intent(LoginActivity.this,MusicPlayerActivity.class);
                startActivity(music);
            }
        });

        googleSignin=findViewById(R.id.googleSigninBtn);
        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.googleSigninBtn:
                        signIn();
                        break;
                }
            }
        });

        //facebook
        Facebook=findViewById(R.id.fblogin);
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fb=new Intent(LoginActivity.this,FacebookActivity.class);
                startActivity(fb);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Intent mainActivity=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent loginMainActivity=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(loginMainActivity);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
