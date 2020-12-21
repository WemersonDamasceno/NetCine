package com.example.netcine.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.netcine.models.FavoritoFS;
import com.example.netcine.models.Filme;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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
        values.put("tipo_midia", filme.getTipoMidia());
        Log.i("teste","Adicionado com id do filme: "+filme.getIdFilme());
        banco.insert("favorito", null, values);

        FavoritoFS fs = new FavoritoFS();
        fs.setTipo_midia(filme.getTipoMidia());
        fs.setIdFilme(filme.getIdFilme());
        fs.setIdUsuario(FirebaseAuth.getInstance().getUid());

        //Salvar o favorito no firebase
        salvarFavoritoFirebase(fs);

    }


    public void salvarFavoritoFirebase(FavoritoFS favoritoFS){
        FirebaseFirestore.getInstance().collection("favoritos/")
                .add(favoritoFS)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //salvo com sucesso
                        Log.i("teste","Favorito salvo no firebase");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("teste", "erro ao salvar no firebase: "+e.getMessage());
            }
        });
    }

    public void deletarFavoritoFirebase(final FavoritoFS favoritoFS){
        FirebaseFirestore.getInstance().collection("/favoritos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs){
                            FavoritoFS fs = doc.toObject(FavoritoFS.class);
                            if(fs.getIdFilme().equals(favoritoFS.getIdFilme())){
                                FirebaseFirestore.getInstance().collection("/favoritos")
                                        .document(doc.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.i("teste", "Sucesso ao deletar favorito");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("teste", "Falha ao deletar favorito: "+e.getMessage());
                                    }
                                });
                            }
                        }
                    }
                });
    }





    public ArrayList<FavoritoFS> getListFavoritos(){
        ArrayList<FavoritoFS> favoritos = new ArrayList<>();

        @SuppressLint("Recycle") Cursor cursor = banco.query(
                "favorito",
                new String[]{"id_user", "id_filme", "tipo_midia"},
                null,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            FavoritoFS favorito = new FavoritoFS();
            favorito.setIdUsuario(cursor.getString(0));
            favorito.setIdFilme(cursor.getString(1));
            favorito.setTipo_midia(cursor.getString(2));

            favoritos.add(favorito);
        }
        return favoritos;
    }



}
