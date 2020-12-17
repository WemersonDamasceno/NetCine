package com.example.netcine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Filme implements Parcelable {
    String tituloFilme;
    String caminhoPosterFilme;
    String descricaoFilme;
    String idFilme;
    double notaFilme;
    boolean favoritoFilme;

    public Filme(String tituloFilme, String caminhoPosterFilme, String descricaoFilme, String idFilme, double notaFilme, boolean favoritoFilme) {
        this.tituloFilme = tituloFilme;
        this.caminhoPosterFilme = caminhoPosterFilme;
        this.descricaoFilme = descricaoFilme;
        this.idFilme = idFilme;
        this.notaFilme = notaFilme;
        this.favoritoFilme = favoritoFilme;
    }

    public Filme() {
    }

    protected Filme(Parcel in) {
        tituloFilme = in.readString();
        caminhoPosterFilme = in.readString();
        descricaoFilme = in.readString();
        idFilme = in.readString();
        notaFilme = in.readDouble();
        favoritoFilme = in.readByte() != 0;
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

    public String getCaminhoPosterFilme() {
        return caminhoPosterFilme;
    }

    public void setCaminhoPosterFilme(String caminhoPosterFilme) {
        this.caminhoPosterFilme = caminhoPosterFilme;
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

    public boolean isFavoritoFilme() {
        return favoritoFilme;
    }

    public void setFavoritoFilme(boolean favoritoFilme) {
        this.favoritoFilme = favoritoFilme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tituloFilme);
        parcel.writeString(caminhoPosterFilme);
        parcel.writeString(descricaoFilme);
        parcel.writeString(idFilme);
        parcel.writeDouble(notaFilme);
        parcel.writeByte((byte) (favoritoFilme ? 1 : 0));
    }
}
