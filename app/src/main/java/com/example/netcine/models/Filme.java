package com.example.netcine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Filme implements Parcelable {
    String tituloFilme;
    String urlPoster;
    String descricaoFilme;
    String idFilme;
    double notaFilme;
    String dataLancamento;
    String linguagem;
    String tipoMidia;

    public Filme() {
    }

    protected Filme(Parcel in) {
        tituloFilme = in.readString();
        urlPoster = in.readString();
        descricaoFilme = in.readString();
        idFilme = in.readString();
        notaFilme = in.readDouble();
        dataLancamento = in.readString();
        linguagem = in.readString();
        tipoMidia = in.readString();
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    public String getTituloFilme() {
        return tituloFilme;
    }

    public void setTituloFilme(String tituloFilme) {
        this.tituloFilme = tituloFilme;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getDescricaoFilme() {
        return descricaoFilme;
    }

    public void setDescricaoFilme(String descricaoFilme) {
        this.descricaoFilme = descricaoFilme;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(String idFilme) {
        this.idFilme = idFilme;
    }

    public double getNotaFilme() {
        return notaFilme;
    }

    public void setNotaFilme(double notaFilme) {
        this.notaFilme = notaFilme;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }


    public String getTipoMidia() {
        return tipoMidia;
    }

    public void setTipoMidia(String tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    public Filme(String tituloFilme, String urlPoster, String descricaoFilme, String idFilme, double notaFilme, String dataLancamento, String linguagem,String tipoMidia) {
        this.tituloFilme = tituloFilme;
        this.urlPoster = urlPoster;
        this.descricaoFilme = descricaoFilme;
        this.idFilme = idFilme;
        this.notaFilme = notaFilme;
        this.dataLancamento = dataLancamento;
        this.linguagem = linguagem;
        this.tipoMidia = tipoMidia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tituloFilme);
        parcel.writeString(urlPoster);
        parcel.writeString(descricaoFilme);
        parcel.writeString(idFilme);
        parcel.writeDouble(notaFilme);
        parcel.writeString(dataLancamento);
        parcel.writeString(linguagem);
        parcel.writeString(tipoMidia);
    }
}
