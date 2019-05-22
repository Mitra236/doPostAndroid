package com.example.dopostemail.server;

import com.example.dopostemail.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MessagesInterface {

    @GET("messages")
    Call<ArrayList<Message>> getMessages();

    @GET("messages/{id}")
    Call<Message> getMessageById(@Path("id") int id);

    @GET("sortDesc")
    Call<ArrayList<Message>> getAllMessagesDesc();

    @GET("sortAsc")
    Call<ArrayList<Message>> getAllMessagesAsc();

    @DELETE("messages/{id}")
    Call<Message> deleteMessage(@Path("id") int id);

    @PUT("messages/edit")
    Call<Message> editMessage(@Body String params);

    @PUT("messages/add")
    Call<Message> addMessage(@Body String params);
}
