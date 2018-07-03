package com.canadatravel.api;

import com.canadatravel.model.DataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;



public interface ApiServices {

    @GET("facts.json")
    Call<DataModel> getFeed();


}