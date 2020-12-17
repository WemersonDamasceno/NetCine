package com.example.netcine.service;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmeService {

    @GET("movies/popular")
    Call<ResponseBody> obterFilmesPopulares(@Query("api_key") String chaveApi);


}
