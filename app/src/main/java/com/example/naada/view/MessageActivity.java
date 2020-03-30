package com.example.naada.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.naada.R;
import com.example.naada.data.adapters.JsonPlaceHolderApi;
import com.example.naada.data.adapters.MessageAdapter;
import com.example.naada.data.models.Post;
import com.example.naada.data.models.ResponseMessage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    EditText userInput;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<ResponseMessage> responseMessageList;
    String message="";
    String id="";
    private ImageView backBtn;
    GoogleSignInClient mGoogleSignInClient;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        backBtn=findViewById(R.id.backBtn);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        assert acct != null;
        name= acct.getDisplayName();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(MessageActivity.this,MusicPlayerActivity.class);
                startActivity(back);
                finish();
            }
        });

        messageAdapter = new MessageAdapter(responseMessageList, this);
        try{
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        }catch(Exception e){}
        try{
            recyclerView.setAdapter(messageAdapter);
        }catch(Exception e){}

        Button send = findViewById(R.id.sendBtn);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://fast-garden-54651.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
        getPosts();
        //getComments();

        try{
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createPost();
                }
            });
        }catch(Exception e){}

    }

    private void getPosts(){
        Call<List<Post>> call=jsonPlaceHolderApi.getPosts(0);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts=response.body();
                for(Post post:posts){

                    message=post.getMessage();
                    id=post.getsender();

                    if (id.equals(name)) {
                        ResponseMessage responseMessage = new ResponseMessage(message, true,name);
                        responseMessageList.add(responseMessage);
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    }else{
                        ResponseMessage responseMessage2 = new ResponseMessage(message, false,id);
                        responseMessageList.add(responseMessage2);
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                        try{
                            userInput.setText("");
                        }catch(Exception e){}

                    }
                    if (!isLastVisible())
                        try{
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }catch(Exception e){}

                }
            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
    private void createPost(){
        message=userInput.getText().toString();
        id=name;
        if(message.isEmpty()){
            Toast.makeText(this, "Can't send empty message", Toast.LENGTH_SHORT).show();
        }else{
            Post post=new Post(message,id);
            Call<Post> call=jsonPlaceHolderApi.createPost(post);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    try{
                        getPosts();
                    }catch(Exception e){}
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                }
            });
        }
    }
    boolean isLastVisible() {
        try{
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            int pos = layoutManager.findLastCompletelyVisibleItemPosition();
            int numItems = recyclerView.getAdapter().getItemCount();
            return (pos >= numItems);
        }catch(Exception e){}

       return false;
    }
}