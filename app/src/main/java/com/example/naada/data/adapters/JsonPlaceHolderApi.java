package com.example.naada.data.adapters;

import com.example.naada.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("message/{no}")
    Call<List<Post>> getPosts(@Path("no") int number);

    @POST("message/0")
    Call<Post> createPost(@Body Post post);

}