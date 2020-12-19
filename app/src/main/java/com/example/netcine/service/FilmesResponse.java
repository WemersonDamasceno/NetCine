package com.example.netcine.service;


import com.squareup.moshi.Json;

public class FilmesResponse {
    @Json(name = "poster_path")
    private final String caminhoPoster;
    @Json(name= "original_title")
    private final String tituloOriginal;
    @Json(name =  "original_name")
    private final String nomeSerie;


    public FilmesResponse(String caminhoPoster, String tituloOriginal, String nomeSerie) {
        this.caminhoPoster = caminhoPoster;
        this.tituloOriginal = tituloOriginal;
        this.nomeSerie = nomeSerie;
    }


    public String getCaminhoPoster() {
        return caminhoPoster;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public String getNomeSerie() {
        return nomeSerie;
    }
}
