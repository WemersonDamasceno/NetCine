package com.example.netcine.models;

public class Favorito {
    private String idUsuario;
    private String idFilme;
    private String tipo_midia;

    public Favorito() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(String idFilme) {
        this.idFilme = idFilme;
    }

    public String getTipo_midia() {
        return tipo_midia;
    }

    public void setTipo_midia(String tipo_midia) {
        this.tipo_midia = tipo_midia;
    }

    public Favorito(String idUsuario, String idFilme, String tipo_midia) {
        this.idUsuario = idUsuario;
        this.idFilme = idFilme;
        this.tipo_midia = tipo_midia;
    }
}
