package com.example.dopostemail.server;

import com.example.dopostemail.model.Contact;
import com.example.dopostemail.model.Folder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoldersInterface {

    @GET("folders")
    Call<ArrayList<Folder>> getFolders();

    @GET("folders/{id}")
    Call<Folder> getFoldersById(@Path("id") int id);

    @POST("folders/add")
    Call<Folder> addFolder(@Body Folder folder);

    @PUT("folders/update")
    Call<Folder> updateFolder(@Body Folder folder);

    @DELETE("folders/{id}")
    Call<Folder> deleteFolder(@Path("id") int id);
}
