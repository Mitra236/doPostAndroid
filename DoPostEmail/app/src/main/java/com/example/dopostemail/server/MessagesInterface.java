package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;
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

//    @POST("messages")
//    Call<ArrayList<Message>> getMessages(@Body Account acc);

    @GET("api/messages")
    Call<ArrayList<Message>> getMessages();

    @GET("api/message")
    Call<Message> getMessage(@Query("id") Long id);

    @GET("sortDesc")
    Call<ArrayList<Message>> getAllMessagesDesc();

    @GET("sortAsc")
    Call<ArrayList<Message>> getAllMessagesAsc();

    @DELETE("api/messages")
    Call<Message> deleteMessage(@Query("id") Long id);

    @PUT("messages/edit")
    Call<Message> editMessage(@Query ("id") Long id, @Body Message message);

    @POST("messages/add")
    Call<Message> addMessage(@Body Message params);

    @POST("send")
    Call<Message> sendMessage(@Body Message msg);

    @POST("check")
    Call<Message> checkMessages(@Body Account account);

    @POST("messages/draft")
    Call<Message> draftMessage(@Body Message params);
}
