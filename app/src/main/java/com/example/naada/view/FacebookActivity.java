package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.naada.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton facebookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        facebookBtn=findViewById(R.id.facebookLogin);

        facebookBtn.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        facebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String userid=loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String first_name, last_name, email = null, id=null, name = null;
                        try {
                            first_name = object.getString("first_name");
                            last_name = object.getString("last_name");
                            name=first_name+last_name;
                            email = object.getString("email");
                            id = object.getString("id");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        Intent FbLogin=new Intent(FacebookActivity.this,MainActivity.class);
                        FbLogin.putExtra("email",email);
                        FbLogin.putExtra("name",name);
                        FbLogin.putExtra("id",id);
                        startActivity(FbLogin);
                        finish();
                    }
                });

                Bundle parameters=new Bundle();
                parameters.putString("fields","first_name, last_name, email, id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
