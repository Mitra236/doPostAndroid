package com.example.dopostemail.server;

import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;
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

public interface FoldersInterface {

    @GET("api/folders")
    Call<ArrayList<Folder>> getFolders();

    @GET("api/folder")
    Call<Folder> getFolder(@Path("id") int id);

    @POST("api/folders")
    Call<Folder> saveFolder(@Body Folder folder);

    @PUT("api/folders/{id}")
    Call<Folder> updateFolder(@Body Folder folder, @Path("id") Long id);

    @DELETE("api/folders/{id}")
    Call<Folder> deleteFolder(@Query("id") Long id);

    @GET("foldermesages")
    Call<ArrayList<Message>> getFolderMessageList();

    @GET("foldermessages/{id}")
    Call<Message> getFolderMessageById(@Path("id") int id);
}
