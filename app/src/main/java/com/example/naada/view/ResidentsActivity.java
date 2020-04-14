package com.example.naada.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naada.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.naada.util.BottomNavHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ResidentsActivity extends AppCompatActivity {
    ArrayList<resident> residents=new ArrayList<>();
    private resident_adapter recylerview;
    private RecyclerView cars_recyclerview;
    TextView checkbio;
    private FloatingActionButton mMainFab,mDarkFab,mMusicFab;
    private TextView mDarkFabText,mMusicFabText;
    private Boolean isOpen;
    private Animation mFabOpen,mFabClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_residents);
        NavBarSetup();

        mMainFab=findViewById(R.id.main_fab);
        mDarkFab=findViewById(R.id.dark_fab);
        mMusicFab=findViewById(R.id.music_fab);
        mDarkFabText=findViewById(R.id.dark_fab_text);
        mMusicFabText=findViewById(R.id.music_fab_text);
        mFabOpen= AnimationUtils.loadAnimation(ResidentsActivity.this,R.anim.fab_open);
        mFabClose=AnimationUtils.loadAnimation(ResidentsActivity.this,R.anim.fab_close);

        cars_recyclerview=(RecyclerView)findViewById( R.id.recylerview);
        cars_recyclerview.setLayoutManager(new GridLayoutManager(this,2));

        getCarsResponse();

        mDarkFab.setVisibility(View.INVISIBLE);
        mMusicFab.setVisibility(View.INVISIBLE);
        mDarkFabText.setVisibility(View.INVISIBLE);
        mMusicFabText.setVisibility(View.INVISIBLE);
        isOpen=false;
        mMainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    mDarkFab.setAnimation(mFabClose);
                    mMusicFab.setAnimation(mFabClose);
                    mDarkFabText.setVisibility(View.INVISIBLE);
                    mMusicFabText.setVisibility(View.INVISIBLE);
                    isOpen=false;
                }else{
                    mDarkFab.setAnimation(mFabOpen);
                    mMusicFab.setAnimation(mFabOpen);
                    mDarkFabText.setVisibility(View.VISIBLE);
                    mMusicFabText.setVisibility(View.VISIBLE);
                    isOpen=true;
                }
            }
        });

        //Darkmode
        mDarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dk=new Intent(ResidentsActivity.this, NightMode.class);
                startActivity(dk);
                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

        mMusicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent music_activity=new Intent(ResidentsActivity.this,MusicPlayerActivity.class);
                startActivity(music_activity);
                mDarkFab.setAnimation(mFabClose);
                mMusicFab.setAnimation(mFabClose);
                mDarkFabText.setVisibility(View.INVISIBLE);
                mMusicFabText.setVisibility(View.INVISIBLE);
                isOpen=false;
            }
        });

    }

    private void NavBarSetup() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
        BottomNavHelper.switchActivities(ResidentsActivity.this,bottomNavigationView);
    }

    private void getCarsResponse() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://fast-garden-54651.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        RequestInteface requestInteface=retrofit.create( RequestInteface.class);
        Call<List<resident>> call=requestInteface.getCarsJson();

        call.enqueue(new Callback<List<resident>>() {
            @Override
            public void onResponse(Call<List<resident>> call, Response<List<resident>> response) {
                residents=new ArrayList<>(response.body());
                recylerview=new resident_adapter( ResidentsActivity.this, residents, new resident_adapter.OnItemClickListener() {
                    @Override
                    public void CheckBioClick(int position) {

                        Intent intent=new Intent( ResidentsActivity.this,artist_bio.class);
                        String x=residents.get( position ).getArtist_id();
                        //Toast.makeText( ResidentsActivity.this,"position"+(x), Toast.LENGTH_SHORT).show();
                        intent.putExtra( "res_id",x );
                        startActivity( intent );
                    }
                } );
                cars_recyclerview.setAdapter(recylerview);

            }

            @Override
            public void onFailure(Call<List<resident>> call, Throwable t) {
                Toast.makeText( ResidentsActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  public void setButtons() {
      checkbio = findViewById(R.id.checkbio);


        checkbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });*/


}
