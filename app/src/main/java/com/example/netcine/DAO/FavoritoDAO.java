package com.example.netcine.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.netcine.models.FavoritoFS;
import com.example.netcine.models.Filme;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FavoritoDAO {
    private SQLiteDatabase banco;

    public FavoritoDAO(Context context) {
        ConexaoSQLite conexao = new ConexaoSQLite(context);
        this.banco = conexao.getWritableDatabase();
    }


    public void desfavoritarFilmeSQLite(Filme filme){
        banco.delete("favorito", "id_filme= ?", new String[]{filme.getIdFilme()});
    }

    public void favoritarFilmeSQLite(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("id_filme", filme.getIdFilme());
        values.put("id_user", FirebaseAuth.getInstance().getUid());
        Log.i("teste","Adicionado com id do filme: "+filme.getIdFilme());
        banco.insert("favorito", null, values);
    }

    public ArrayList<FavoritoFS> getListFavoritos(){
        ArrayList<FavoritoFS> favoritos = new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = banco.query(
                "favorito",
                new String[]{"id_user", "id_filme"},
                null,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            FavoritoFS favorito = new FavoritoFS();
            favorito.setIdUsuario(cursor.getString(0));
            favorito.setFilmeFav(cursor.getString(1));

            favoritos.add(favorito);
        }
        return favoritos;
    }



}
