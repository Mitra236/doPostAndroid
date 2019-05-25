package com.example.dopostemail.server;

import com.example.dopostemail.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessagesInterface {

    @GET("messages")
    Call<ArrayList<Message>> getMessages();

    @GET("api/message")
    Call<Message> getMessageById(@Query("id") int id);

    @GET("sortDesc")
    Call<ArrayList<Message>> getAllMessagesDesc();

    @GET("sortAsc")
    Call<ArrayList<Message>> getAllMessagesAsc();

    @DELETE("api/messages")
    Call<Message> deleteMessage(@Query("id") int id);

    @PUT("messages/edit")
    Call<Message> editMessage(@Query ("id") int id, @Body Message message);

    @POST("messages/add")
    Call<Message> addMessage(@Body String params);

    @POST("messages/draft")
    Call<Message> draftMessage(@Body String params);
}
