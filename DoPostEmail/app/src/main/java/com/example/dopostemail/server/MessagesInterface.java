package com.example.dopostemail.server;

import com.example.dopostemail.model.Account;
import com.example.dopostemail.model.Folder;
import com.example.dopostemail.model.Message;
import com.example.dopostemail.model.Tag;

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

    @DELETE("api/messages/{id}")
    Call<Void> deleteMessage(@Path("id") Long id);

    @PUT("api/messages/{id}")
    Call<Message> updateMessage(@Path ("id") Long id, @Body Message message);

    @POST("api/messages")
    Call<Message> saveMessage(@Body Message params);

    @POST("api/javaMail/send")
    Call<Void> send(@Body Message msg);

    @POST("api/javaMail/check")
    Call<Void> check(@Body Account account);

    @POST("messages/draft")
    Call<Message> draftMessage(@Body Message params);

    @POST("api/messages/findFolderMessages/{id}")
    Call<ArrayList<Message>> findFolderMessages(@Body Folder folder, @Path("id") Long id);

    @POST("api/messages/setFolderMessage/{accId}/{folderId}")
    Call<Message> setFolderMessage(@Body Message msg, @Path("accId") Long id1, @Path("folderId") Long id2);
}
