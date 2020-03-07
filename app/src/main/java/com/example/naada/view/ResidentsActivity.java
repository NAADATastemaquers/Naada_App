package com.example.naada.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naada.R;
import com.example.naada.util.BottomNavHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResidentsActivity extends AppCompatActivity {
    ArrayList<resident> residents=new ArrayList<>();
    private resident_adapter recylerview;
    private RecyclerView cars_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residents);

        cars_recyclerview=(RecyclerView)findViewById(R.id.recylerview);
        cars_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getCarsResponse();

    }

    private void getCarsResponse() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://fast-garden-54651.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        RequestInteface requestInteface=retrofit.create(RequestInteface.class);
        Call<List<resident>> call=requestInteface.getCarsJson();

        call.enqueue(new Callback<List<resident>>() {
            @Override
            public void onResponse(Call<List<resident>> call, Response<List<resident>> response) {
                residents=new ArrayList<>(response.body());
                recylerview=new resident_adapter(ResidentsActivity.this,residents);
                cars_recyclerview.setAdapter(recylerview);
                Toast.makeText(ResidentsActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onFailure(Call<List<resident>> call, Throwable t) {
                Toast.makeText(ResidentsActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void NavBarSetup() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottonNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
        BottomNavHelper.switchActivities(ResidentsActivity.this,bottomNavigationView);
    }
}
