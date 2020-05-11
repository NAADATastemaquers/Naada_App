package com.tastemaquers.naada.view;

import com.tastemaquers.naada.data.adapters.fav_details;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Favorite {
    @GET("favorite/{email}")
    Call<List<fav_details>> getFavSongs(@Path("email") String email);

    @POST("favorite/0")
    Call<fav_details> sendFavSong(@Body fav_details fav_details);
}
