package com.example.netcine.models;

import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.service.FilmesResponse;

public class Convert {
    public static void converterClassFilmeSerie(Filme filme, FilmesResponse f,AdapterFilme adapterFilme){
        filme.setUrlPoster(f.getUrlImgPoster());
        filme.setNotaFilme(f.getNota());


        if(f.getTituloOriginal() ==null) {
            filme.setTituloFilme(f.getNomeSerie());
        }else{
            filme.setTituloFilme(f.getTituloOriginal());
        }


        if(f.getLancamento()==null){
            filme.setDataLancamento(f.getDataLancSerie());
        }else {
            filme.setDataLancamento(f.getLancamento());
        }

        filme.setIdFilme(f.getIdResponse()+"");
        filme.setLinguagem(f.getLinguagem());
        filme.setDescricaoFilme(f.getDescricao());

        adapterFilme.add(filme);
    }

}
