package com.example.dopostemail.server;

import com.example.dopostemail.model.Photo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PhotoInterface {

    @POST("api/photos")
    Call<Photo> savePhoto(@Body Photo photo);
}
