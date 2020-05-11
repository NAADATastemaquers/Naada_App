package com.tastemaquers.naada.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.tastemaquers.naada.R;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private static final String TAG = "Register Activity";
    private CountryCodePicker ccp;
    private EditText phoneText;
    private EditText codeText;
    private Button nxtBtn;
    private String checker="", phoneNumber="";
    private RelativeLayout relativeLayout;
    private TextView carrier;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mAuth=FirebaseAuth.getInstance();
        mProgressDialog=new ProgressDialog(this);

        ccp=findViewById(R.id.ccp);
        phoneText=findViewById(R.id.phoneText);
        codeText=findViewById(R.id.codeText);
        nxtBtn=findViewById(R.id.NextButton);
        relativeLayout=findViewById(R.id.relativeLayout);
        carrier=findViewById(R.id.carrier);

        ccp.registerCarrierNumberEditText(phoneText);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nxtBtn.getText().equals("Submit") || checker.equals("code_sent")){
                    codeText.setVisibility(View.VISIBLE);
                    carrier.setText("");
                    String verificationCode = codeText.getText().toString();
                    if(verificationCode.isEmpty()){
                        Toast.makeText(PhoneActivity.this, "please enter the verification code", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mProgressDialog.setTitle("Verifying verification code");
                        mProgressDialog.setMessage("Please be patient while we verify the code ");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();

                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    }
                }
                else{
                    phoneNumber=ccp.getFullNumberWithPlus();
                    if (!phoneNumber.isEmpty()){

                        mProgressDialog.setTitle("Registering phone number");
                        mProgressDialog.setMessage("Please be patient while we register your phone number");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                PhoneActivity.this,
                                mCallbacks);       // OnVerificationStateChangedCallbacks
                    }
                    else{
                        Toast.makeText(PhoneActivity.this, "Please enter proper format of phone number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                mProgressDialog.dismiss();
                Toast.makeText(PhoneActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                relativeLayout.setVisibility(View.VISIBLE);

                nxtBtn.setText("Next");
                codeText.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId=s;
                mResendToken=forceResendingToken;

                relativeLayout.setVisibility(View.GONE);
                checker="code_sent";
                nxtBtn.setText("Submit");
                carrier.setText("");
                codeText.setVisibility(View.VISIBLE);

                mProgressDialog.dismiss();
                Toast.makeText(PhoneActivity.this, "Verification code sent to your phone number", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            mProgressDialog.dismiss();
                            Toast.makeText(PhoneActivity.this, "Registration completed!", Toast.LENGTH_SHORT).show();
                            intentMainactivity();
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(PhoneActivity.this, "Registration failed :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void intentMainactivity(){
        Intent mainActivity = new Intent(PhoneActivity.this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent main = new Intent(PhoneActivity.this,MainActivity.class);
            startActivity(main);
            finish();
        }
    }
}

