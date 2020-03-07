package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naada.R;

public class SignUp extends AppCompatActivity {

    private ImageView backBtn;
    private TextView signUpLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backLoginPage=new Intent(SignUp.this,LoginActivity.class);
                startActivity(backLoginPage);
                finish();
            }
        });

        signUpLoginBtn=findViewById(R.id.loginBtn);
        signUpLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backLoginPage=new Intent(SignUp.this,LoginActivity.class);
                startActivity(backLoginPage);
                finish();
            }
        });
    }
}
