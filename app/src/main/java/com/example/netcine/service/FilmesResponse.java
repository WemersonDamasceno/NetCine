package com.example.netcine.service;


import com.squareup.moshi.Json;

public class FilmesResponse {
    @Json(name = "poster_path")
    private final String urlImgPoster;
    @Json(name= "original_title")
    private final String tituloOriginal;
    @Json(name =  "original_name")
    private final String nomeSerie;
    @Json(name= "vote_average")
    private final double nota;
    @Json(name="release_date")
    private final String lancamento;
    @Json(name="id")
    private final int idResponse;
    @Json(name="original_language")
    private final String linguagem;
    @Json(name = "overview")
    private final String descricao;
    @Json(name = "first_air_date")
    private final String dataLancSerie;


    public FilmesResponse(String urlImgPoster, String tituloOriginal, String nomeSerie,
                          double nota, String lancamento, int idResponse, String linguagem,
                          String descricao, String dataLancSerie) {
        this.urlImgPoster = urlImgPoster;
        this.tituloOriginal = tituloOriginal;
        this.nomeSerie = nomeSerie;
        this.nota = nota;
        this.lancamento = lancamento;
        this.idResponse = idResponse;
        this.linguagem = linguagem;
        this.descricao = descricao;
        this.dataLancSerie = dataLancSerie;
    }

    public String getDataLancSerie() {
        return dataLancSerie;
    }

    public String getUrlImgPoster() {
        return urlImgPoster;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public String getNomeSerie() {
        return nomeSerie;
    }

    public double getNota() {
        return nota;
    }

    public String getLancamento() {
        return lancamento;
    }

    public int getIdResponse() {
        return idResponse;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public String getDescricao() {
        return descricao;
    }
}
