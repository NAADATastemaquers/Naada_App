package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.airbnb.lottie.LottieAnimationView;
import com.example.naada.R;

public class LoginActivity extends AppCompatActivity {

    private TextView signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Play=findViewById(R.id.animation);
//        Play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "Go To Music Player", Toast.LENGTH_SHORT).show();
//            }
//        });


        signUpBtn=findViewById(R.id.signUptxt);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUppage=new Intent(LoginActivity.this,SignUp.class);
                startActivity(signUppage);
                finish();
            }
        });
    }
}
