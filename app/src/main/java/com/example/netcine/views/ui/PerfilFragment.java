package com.example.netcine.views.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netcine.DAO.FavoritoDAO;
import com.example.netcine.R;
import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.models.Convert;
import com.example.netcine.models.FavoritoFS;
import com.example.netcine.models.Filme;
import com.example.netcine.service.ApiService;
import com.example.netcine.service.FilmesResponse;

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
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setReverseLayout(false);
        favoritoDAO = new FavoritoDAO(getContext());


        rvFilmesFavoritos.setAdapter(adapterFilme);
        rvFilmesFavoritos.setLayoutManager(manager);

        buscarFilmes();


        return view;
    }

    private void buscarFilmes() {
        listFavoritos = favoritoDAO.getListFavoritos();
        //pegar todos os filmes favoritos
        for (FavoritoFS f : listFavoritos) {
            //é serie ou filme ?
            if(f.getTipo_midia().equals("filme")) {
               buscarFilmeAPI(f);
            }else if(f.getTipo_midia().equals("serie")){
                buscarSerieAPI(f);
            }
        }

    }

    private void buscarSerieAPI(FavoritoFS f) {
        ApiService.getInstanceRetrofit().getSerie(Integer.parseInt(f.getIdFilme()), api_key)
                .enqueue(new Callback<FilmesResponse>() {
                    @Override
                    public void onResponse(Call<FilmesResponse> call, Response<FilmesResponse> response) {
                        FilmesResponse fR = response.body();
                        Filme filme = new Filme();
                        filme.setTipoMidia("serie");
                        Log.i("teste", "tipo midia: "+filme.getTipoMidia());
                        Convert.converterClassFilmeSerie(filme, fR, adapterFilme);
                    }

                    @Override
                    public void onFailure(Call<FilmesResponse> call, Throwable t) {
                    }
                });
    }

    private void buscarFilmeAPI(FavoritoFS f) {
        ApiService.getInstanceRetrofit().getFilme(Integer.parseInt(f.getIdFilme()), api_key)
                .enqueue(new Callback<FilmesResponse>() {
                    @Override
                    public void onResponse(Call<FilmesResponse> call, Response<FilmesResponse> response) {
                        FilmesResponse fR = response.body();
                        Filme filme = new Filme();
                        Log.i("teste", "tipo midia: "+filme.getTipoMidia());

                        Convert.converterClassFilmeSerie(filme, fR, adapterFilme);
                    }

                    @Override
                    public void onFailure(Call<FilmesResponse> call, Throwable t) {
                    }
                });
    }
}