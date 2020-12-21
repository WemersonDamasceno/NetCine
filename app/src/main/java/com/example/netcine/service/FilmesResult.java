package com.example.netcine.service;

import com.squareup.moshi.Json;

import java.util.List;

public class FilmesResult {

    @Json(name = "results")
    private final List<FilmesResponse> resultadoFilmes;

    public List<FilmesResponse> getResultadoFilmes() {
        return resultadoFilmes;
    }



    public FilmesResult(List<FilmesResponse> resultadoFilmes) {
        this.resultadoFilmes = resultadoFilmes;
    }
}
