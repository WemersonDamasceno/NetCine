package com.example.netcine.models;

import java.util.List;

public class FavoritoFS {
    private String idUsuario;
    private String idFilme;


    public FavoritoFS(String idUsuario, String filme) {
        this.idUsuario = idUsuario;
        this.idFilme = filme;
    }

    public FavoritoFS() {
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public void setFilmeFav(String idFilmeFav) {
        this.idFilme = idFilmeFav;
    }
}
