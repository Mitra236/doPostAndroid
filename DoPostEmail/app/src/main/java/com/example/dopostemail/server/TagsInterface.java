package com.example.dopostemail.server;

import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TagsInterface {

    @GET("api/tags")
    Call<ArrayList<Tag>> getTags();

    @GET("api/tags/{id}")
    Call<Tag> getTag(@Path("id") Long id);

    @DELETE("api/tags/{id}")
    Call<Void> deleteTag(@Path("id") Long id);

    @PUT("api/tags/{id}")
    Call<Tag> updateTag(@Body Tag tag, @Path ("id") Long id);

}
