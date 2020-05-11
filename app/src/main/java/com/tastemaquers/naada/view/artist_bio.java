package com.tastemaquers.naada.view;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tastemaquers.naada.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class artist_bio extends AppCompatActivity {
    ImageView profile;
    TextView name,desc,rate;
    RequestInteface request;
    ArrayList<artist_shows> show=new ArrayList<>();
    art_bio bio;
    private shows_adapter adapter;
    private RecyclerView recyclerView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_artist_bio );

        profile=(ImageView)findViewById( R.id.artist_img );
        name=(TextView) findViewById( R.id.art_name );
        rate=(TextView) findViewById( R.id.rate );
        desc=(TextView) findViewById( R.id.abt );
        recyclerView=(RecyclerView)findViewById(R.id.recyle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            id=(String)bundle.get( "res_id" );
        }

        getresponse2();
    }
    public  void getresponse2()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://fast-garden-54651.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        request=retrofit.create( RequestInteface.class);
        Call<art_bio> call1=request.getbio(id);

        call1.enqueue( new Callback<art_bio>() {
            @Override
            public void onResponse(Call<art_bio> call, Response<art_bio> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText( artist_bio.this,"failure",Toast.LENGTH_SHORT).show();
                    desc.setText("code"+ response.code() );
                    return;

                }



                art_bio s=response.body();
                name.setText( s.getArtist() );
                desc.setText( s.getArtistDesc() );
                rate.setText( "Rate "+s.getArtist() );
                // String ex=bio.getArtistImg();
                // x(ex);
                String ex=s.getArtistImg();
                Picasso.get().load(ex).into(profile);
                show= new ArrayList<>( response.body().getArtistShows() ) ;

                adapter=new shows_adapter( artist_bio.this,show );
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<art_bio> call, Throwable t) {
                Toast.makeText( artist_bio.this,"failure",Toast.LENGTH_SHORT).show();
                return;

            }
        } );

    }
    void x( String ex)
    {
        Picasso.get().load(ex).resize( 50,50 ).centerCrop().into(profile);
    }
}
