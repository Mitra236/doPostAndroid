package com.example.dopostemail.server;

import com.example.dopostemail.model.Folder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoldersInterface {

    @GET("folders")
    Call<ArrayList<Folder>> getFolders();

    @GET("folders/{id}")
    Call<Folder> getFoldersById(@Path("id") int id);
}
