package com.example.netcine.views.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netcine.DAO.FavoritoDAO;
import com.example.netcine.R;
import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.models.Convert;
import com.example.netcine.models.Favorito;
import com.example.netcine.models.Filme;
import com.example.netcine.models.User;
import com.example.netcine.service.ApiService;
import com.example.netcine.service.FilmesResponse;
import com.example.netcine.views.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {

    private RecyclerView rvFilmesFavoritos;
    private AdapterFilme adapterFilme;
    private ImageView imgTrocarFotoPerfil;
    private ProgressDialog progressDialogFoto;
    private  ArrayList<Favorito> listFavoritos;

    private ImageView imgSair;
    private ImageView imgLost;
    private ImageView imgSincronizar;
    private ImageView imgPerfilUser;
    private ProgressDialog dialog;
    private String api_key = "6ee08abf792ae460668806133c782b4c";
    private FavoritoDAO favoritoDAO;

    public PerfilFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);



        iniciandoVariaveis();

        vinculandoXML(view);

        configRecyclerAdapter();

        //  buscarFilmes();

        setarDadosUser(FirebaseAuth.getInstance().getUid());



        imgSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ainda não implementado!", Toast.LENGTH_LONG).show();
                /*Buscar todas as favoritos do usuario no firebase
                *
                * Ta acontecendo uns erros quando eu tento recuperar os favoritos que
                * foram salvos no banco de dados, */
                //buscarFavoritos(FirebaseAuth.getInstance().getUid());
            }
        });

        imgSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(view.getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        imgTrocarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });


        return view; 
    }

    @Override
    public void onStart() {
        adapterFilme.apagarList();
        buscarFilmes();
        super.onStart();

    }

    private void iniciandoVariaveis() {
        //ArrayList<Filme> listFilmes = new ArrayList<>();
        //ArrayList<Favorito> listFavoritosFirebase = new ArrayList<>();
        dialog = new ProgressDialog(getContext());
        progressDialogFoto = new ProgressDialog(getContext());
    }

    private void vinculandoXML(View view) {
        rvFilmesFavoritos = view.findViewById(R.id.rvFilmesFavoritos);
        imgTrocarFotoPerfil = view.findViewById(R.id.imgTrocarFotoPerfil);
        imgSair = view.findViewById(R.id.imgSair);
        imgPerfilUser = view.findViewById(R.id.imgPerfilUser);
        imgSincronizar = view.findViewById(R.id.imgSincronizar);
        imgLost = view.findViewById(R.id.imgLost);
    }

    private void configRecyclerAdapter() {
        adapterFilme = new AdapterFilme(getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setReverseLayout(false);
        favoritoDAO = new FavoritoDAO(getContext());


        rvFilmesFavoritos.setAdapter(adapterFilme);
        rvFilmesFavoritos.setLayoutManager(manager);
    }


    private void buscarFilmes() {
        adapterFilme.apagarList();
        listFavoritos = favoritoDAO.getListFavoritos();

        if(listFavoritos.size()==0){
            imgLost.setVisibility(View.VISIBLE);
        }else {
            //pegar todos os filmes favoritos
            for (Favorito f : listFavoritos) {
                //é serie ou filme ?
                if (f.getTipo_midia().equals("filme")) {
                    buscarFilmeAPI(f);
                } else if (f.getTipo_midia().equals("serie")) {
                    buscarSerieAPI(f);
                }
            }
        }

    }

    private void buscarSerieAPI(final Favorito f) {
        ApiService.getInstanceRetrofit().getSerie(Integer.parseInt(f.getIdFilme()), api_key)
                .enqueue(new Callback<FilmesResponse>() {
                    @Override
                    public void onResponse(Call<FilmesResponse> call, Response<FilmesResponse> response) {
                        FilmesResponse fR = response.body();
                        Filme filme = new Filme();

                        Convert.converterClassFilmeSerie(filme, fR, adapterFilme);
                    }

                    @Override
                    public void onFailure(Call<FilmesResponse> call, Throwable t) {
                        Log.i("teste","Erro: "+t.getMessage());
                    }
                });
    }

    private void buscarFilmeAPI(final Favorito f) {
        ApiService.getInstanceRetrofit().getFilme(Integer.parseInt(f.getIdFilme()), api_key)
                .enqueue(new Callback<FilmesResponse>() {
                    @Override
                    public void onResponse(Call<FilmesResponse> call, Response<FilmesResponse> response) {
                        FilmesResponse fR = response.body();
                        Filme filme = new Filme();

                        Convert.converterClassFilmeSerie(filme, fR, adapterFilme);
                    }

                    @Override
                    public void onFailure(Call<FilmesResponse> call, Throwable t) {
                        Log.i("teste","Erro: "+t.getMessage());
                    }
                });
    }

    private void setarDadosUser(final String uid) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot  doc : value.getDocuments()){
                            User user = doc.toObject(User.class);
                            if(user.getIdUser().equals(uid)){
                                if(user.getUrlFotoUser() != null) {
                                    Picasso.get().load(user.getUrlFotoUser()).into(imgPerfilUser);
                                }
                            }
                        }
                    }
                });
    }

    private void selecionarFoto() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"SelecionarFoto"),1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            Uri uri = data.getData();
            enviarFoto(uri);
            imgPerfilUser.setImageURI(uri);
        }
    }

    private void enviarFoto(Uri selectedImage) {
        progressDialogFoto.setTitle("Enviando sua foto...");
        progressDialogFoto.show();
        //salvar imagem no banco
        String fileName = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + fileName);
        ref.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri1) {
                                updateUserFoto(uri1.toString());
                                progressDialogFoto.dismiss();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste", "Falha ao fazer upload da foto: " + e.getMessage());
                        if (e.getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")) {
                            Toast.makeText(getContext(), "Falha ao conectar-se com a internet", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialogFoto.setMessage("Enviado: "+ (int) progress + "% completo");
            }
        });

    }
    private void updateUserFoto(final String urlFotoPerfil) {
        FirebaseFirestore.getInstance().collection("/users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            User usuario = doc.toObject(User.class);
                            if(usuario.getIdUser().equals(FirebaseAuth.getInstance().getUid())){
                                FirebaseFirestore.getInstance().collection("/users")
                                        .document(doc.getId())
                                        .update("urlFotoUser", urlFotoPerfil)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.i("teste", "Update completo");
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    //Ainda nao esta sendo usado...
    private void buscarFavoritos(final String uid) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favoritos/")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        //Limpar o banco de dados SQLite
                        favoritoDAO.limparBANCO();
                        for(DocumentSnapshot doc : value.getDocuments()){
                            //Pegar os obj do firebase e add no sqlite
                            Favorito fs = doc.toObject(Favorito.class);
                            favoritoDAO.salvarFavoritoSQLite(fs.getIdFilme(),fs.getTipo_midia());
                        }
                    }

                });
        db.terminate().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
            }
        });
    }


}