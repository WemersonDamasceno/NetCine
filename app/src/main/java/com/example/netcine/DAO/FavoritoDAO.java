package com.example.netcine.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.netcine.models.FavoritoFS;
import com.example.netcine.models.Filme;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {
    private ConexaoSQLite conexao;
    private SQLiteDatabase banco;

    public FavoritoDAO(Context context) {
        this.conexao = new ConexaoSQLite(context);
        this.banco = conexao.getWritableDatabase();
    }


    public void desfavoritarFilmeSQLite(Filme filme){
        banco.delete("favorito", "id_filme= ?", new String[]{filme.getIdFilme()});
    }

    public long favoritarFilmeSQLite(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("id_filme", filme.getIdFilme());
        values.put("id_user", FirebaseAuth.getInstance().getUid());
        Log.i("teste","Adicionado com id do filme: "+filme.getIdFilme());
        return banco.insert("favorito",null,values);
    }

    public ArrayList<FavoritoFS> getListFavoritos(){
        ArrayList<FavoritoFS> favoritos = new ArrayList<>();

        Cursor cursor = banco.query(
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
