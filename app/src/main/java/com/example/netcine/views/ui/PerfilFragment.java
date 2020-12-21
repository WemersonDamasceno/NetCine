package com.example.netcine.views.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.netcine.DAO.FavoritoDAO;
import com.example.netcine.R;
import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.models.FavoritoFS;
import com.example.netcine.models.Filme;
import com.example.netcine.service.ApiService;
import com.example.netcine.service.FilmesResponse;
import com.example.netcine.service.FilmesResult;
import com.example.netcine.views.DetalhesFilmeActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    RecyclerView rvFilmesFavoritos;
    AdapterFilme adapterFilme;
    ArrayList<Filme> listFilmes;
    ArrayList<FavoritoFS> listFavoritos;
    String api_key = "6ee08abf792ae460668806133c782b4c";
    FavoritoDAO favoritoDAO;

    public PerfilFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        listFilmes = new ArrayList<>();
        rvFilmesFavoritos = view.findViewById(R.id.rvFilmesFavoritos);
        adapterFilme = new AdapterFilme(getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setReverseLayout(false);
        favoritoDAO = new FavoritoDAO(getContext());


        rvFilmesFavoritos.setAdapter(adapterFilme);
        rvFilmesFavoritos.setLayoutManager(manager);

        buscarFilmes();


        return view;
    }

    private void checarSeFilmeFavorito() {
        //receber todos os favoritos do banco
        listFavoritos = favoritoDAO.getListFavoritos();

        for(FavoritoFS f : listFavoritos){
            if(f.getIdUsuario().equals(FirebaseAuth.getInstance().getUid())){
                for(Filme f1 : listFilmes){
                    if(f.getIdFilme().equals(f1.getIdFilme())){
                        adapterFilme.add(f1);
                    }
                }
            }
        }

    }

    private void buscarFilmes() {
        //Obter todos os filmes, e todas as series
        //Ainda falta os melhores filmes da semana e do dia, e as series
        ApiService.getInstanceRetrofit().obterFilmesPopulares(api_key)
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {

                            for (FilmesResponse f : response.body().getResultadoFilmes()) {
                                Filme filme = new Filme();
                                filme.setUrlPoster(f.getUrlImgPoster());
                                filme.setTituloFilme(f.getTituloOriginal());

                                filme.setNotaFilme(f.getNota());
                                filme.setDataLancamento(f.getLancamento());
                                filme.setIdFilme(f.getIdResponse()+"");
                                filme.setLinguagem(f.getLinguagem());
                                filme.setDescricaoFilme(f.getDescricao());

                                listFilmes.add(filme);

                                //adapterFilme.add(filme);
                            }

                            checarSeFilmeFavorito();
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {

                    }
                });

    }
}