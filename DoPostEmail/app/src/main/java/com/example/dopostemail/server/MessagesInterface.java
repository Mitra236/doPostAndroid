package com.example.dopostemail.server;

import com.example.dopostemail.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessagesInterface {

    @GET("messages")
    Call<ArrayList<Message>> getMessages();

    @GET("messages/{id}")
    Call<Message> getMessageById(@Path("id") int id);
}
