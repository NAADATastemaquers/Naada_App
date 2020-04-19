package com.example.naada.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naada.R;
import com.example.naada.data.adapters.JsonPlaceHolderApi;
import com.example.naada.data.adapters.MessageAdapter;
import com.example.naada.data.models.Post;
import com.example.naada.data.models.ResponseMessage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    String time="";
    String id="";
    GoogleSignInClient mGoogleSignInClient;
    String name;
    TextView song,artist;

    private static final String KEY_ARTIST= "artist";
    private static final String KEY_NAME="name";
    private static final String TAG = "MessageActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference contentRef= db.collection("songs").document("song");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        artist=findViewById(R.id.artist_name);
        song=findViewById(R.id.song_name);
        responseMessageList = new ArrayList<>();

        contentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    final String song_name =documentSnapshot.getString(KEY_NAME);
                    final String artist_name =documentSnapshot.getString(KEY_ARTIST);
                    Log.d(TAG, "song name: "+song_name);
                    Log.d(TAG, "artist_name: "+artist_name);
                    song.setText(song_name);
                    artist.setText(artist_name);
                }else{
                    Toast.makeText(MessageActivity.this,"​Document doesn't exists​",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    time=post.getTimestamp();
                    time=time.substring(11,16);

                    if (id.equals(name)) {
                        ResponseMessage responseMessage = new ResponseMessage(message, true,name,time);
                        responseMessageList.add(responseMessage);
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    }else{
                        ResponseMessage responseMessage2 = new ResponseMessage(message, false,id,time);
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
        Calendar calendar=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        String time=format.format(calendar.getTime());
        if(message.isEmpty()){
            Toast.makeText(this, "Can't send empty message", Toast.LENGTH_SHORT).show();
        }else{
            Post post=new Post(message,id,time);
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