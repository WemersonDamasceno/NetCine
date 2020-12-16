package com.example.netcine.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String nomeUser;
    private String idUser;
    private String emailUser;
    private String urlFotoUser;


    public User(String nomeUser, String idUser, String emailUser, String urlFotoUser) {
        this.nomeUser = nomeUser;
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.urlFotoUser = urlFotoUser;
    }


    public User() {
    }

    protected User(Parcel in) {
        nomeUser = in.readString();
        idUser = in.readString();
        emailUser = in.readString();
        urlFotoUser = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getUrlFotoUser() {
        return urlFotoUser;
    }

    public void setUrlFotoUser(String urlFotoUser) {
        this.urlFotoUser = urlFotoUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nomeUser);
        parcel.writeString(idUser);
        parcel.writeString(emailUser);
        parcel.writeString(urlFotoUser);
    }
}
