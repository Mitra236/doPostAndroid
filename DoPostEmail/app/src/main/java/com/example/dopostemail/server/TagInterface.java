package com.example.dopostemail.server;

import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TagInterface {

    @POST("api/tags/findTags")
    Call<ArrayList<Tag>> findTags(@Body Message msg);

}
