package com.example.netcine.service;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {
    private static FilmeService instanciaFilmes;

    public static FilmeService getInstanceRetrofit() {
        if(instanciaFilmes == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            instanciaFilmes = retrofit.create(FilmeService.class);
        }

        return instanciaFilmes;
    }
}
