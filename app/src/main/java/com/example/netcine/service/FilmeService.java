package com.example.netcine.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmeService {
    @GET("movie/popular")
    Call<FilmesResult> obterFilmesPopulares(@Query("api_key") String chave_api);

    @GET("tv/popular")
    Call<FilmesResult> obterSeriesPopulares(@Query("api_key") String chave_api);

    @GET("movie/now_playing")
    Call<FilmesResult> obterFilmesRecentes(@Query("api_key") String chave_api);


    @GET("trending/{media_type}/{time_window}")
    Call<FilmesResult> getTrending(@Path("media_type") String media_type,
                                   @Path("time_window") String time_window,
                                   @Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<FilmesResponse> getFilme(@Path("movie_id") int movie_id, @Query("api_key") String chave_api);

    @GET("tv/{tv_id}")
    Call<FilmesResponse> getSerie(@Path("tv_id") int tv_id, @Query("api_key") String chave_api);


}
