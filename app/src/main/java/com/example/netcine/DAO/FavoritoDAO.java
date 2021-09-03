package com.example.netcine.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.netcine.models.Favorito;
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

    public void salvarFavoritoSQLite(String idFilme, String tipo_midia) {
        ContentValues values = new ContentValues();
        values.put("id_filme", idFilme);
        values.put("id_user", FirebaseAuth.getInstance().getUid());
        values.put("tipo_midia", tipo_midia);
        Log.i("teste","Adicionado com id do filme: "+idFilme);
        banco.insert("favorito", null, values);
    }

    public void limparBANCO(){
        banco.execSQL("delete from favorito");
        banco.execSQL("vacuum");
    }





    public ArrayList<Favorito> getListFavoritos(){
        ArrayList<Favorito> favoritos = new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = banco.query(
                "favorito",
                new String[]{"id_user", "id_filme", "tipo_midia"},
                null,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            Favorito favorito = new Favorito();
            favorito.setIdUsuario(cursor.getString(0));
            favorito.setIdFilme(cursor.getString(1));
            favorito.setTipo_midia(cursor.getString(2));

            favoritos.add(favorito);
        }
        return favoritos;
    }



}
