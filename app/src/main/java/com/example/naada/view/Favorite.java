package com.example.naada.view;

import com.example.naada.data.adapters.fav_details;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Favorite {
    @GET("favorite/{email}")
    Call<List<fav_details>> getFavSongs();

    @POST("favorite/0")
    Call<fav_details> sendFavSong(fav_details fav_details);
}
