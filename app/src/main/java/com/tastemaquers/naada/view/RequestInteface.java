package com.tastemaquers.naada.view;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


interface RequestInteface {
    @GET("residents")
    Call<List<resident>> getCarsJson();
   @GET("residents/{id}")
   Call<art_bio> getbio(@Path("id") String id);
}
