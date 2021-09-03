package com.example.netcine.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.netcine.models.Favorito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoritoFirebase {
    public static void adicionarFavorito(final Favorito fs){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favoritos")
                .add(fs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste","Salvo no Firebase!");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        db.terminate().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
    }

}
