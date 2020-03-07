package com.example.naada.view;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


interface RequestInteface {
    @GET("residents")
    Call<List<resident>> getCarsJson();
   // @GET("residents/5e1dcc2f1c9d4400008cb748")
    //Call<art_bio> getbio();
}
