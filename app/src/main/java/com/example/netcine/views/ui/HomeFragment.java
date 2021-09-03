package com.example.netcine.views.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netcine.R;
import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.models.Convert;
import com.example.netcine.models.Filme;
import com.example.netcine.service.ApiService;
import com.example.netcine.service.FilmesResponse;
import com.example.netcine.service.FilmesResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private AdapterFilme adapterFilme;
    private AdapterFilme adapterSerie;
    private final String apiKEY = "6ee08abf792ae460668806133c782b4c";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //configurar recyclerview e o adapter de filmes populares
        confgRVFilmesLancamentos(view);

        //buscar filmes populares e adicionar no adapter
        buscarFilmesPopulares();

        //configurar recyclerview e o adapter de series populares
        confgRVSeriesLancamentos(view);


        buscarSeriesPopulares();


        return view;
    }


    private void confgRVSeriesLancamentos(View view) {
        RecyclerView rvHomeSeriesRecentes = view.findViewById(R.id.rvHomeSeriesRecentes);

        adapterSerie = new AdapterFilme(getContext());
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setReverseLayout(false);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);

        rvHomeSeriesRecentes.setAdapter(adapterSerie);
        rvHomeSeriesRecentes.setLayoutManager(layoutManager1);
    }

    private void confgRVFilmesLancamentos(View view) {
        RecyclerView rvHomeLancamentos = view.findViewById(R.id.rvHomeLancamentos);

        adapterFilme = new AdapterFilme(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        rvHomeLancamentos.setAdapter(adapterFilme);
        rvHomeLancamentos.setLayoutManager(layoutManager);
    }

    private void buscarFilmesPopulares() {
        ApiService.getInstanceRetrofit().obterFilmesPopulares(apiKEY)
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {
                            for (FilmesResponse f : response.body().getResultadoFilmes()) {
                                Filme filme = new Filme();
                                filme.setTipoMidia("filme");
                                Convert.converterClassFilmeSerie(filme, f, adapterFilme);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {
                        Log.i("teste", "erro: " + t.getMessage());
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setTitle("Atenção!");
                        dialog.setMessage("Aconteceu um erro: " + t.getMessage());
                        dialog.setPositiveButton("Ok", null);
                        dialog.show();
                    }
                });
    }

    private void buscarSeriesPopulares() {
        ApiService.getInstanceRetrofit().obterSeriesPopulares(apiKEY)
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {
                            for (FilmesResponse f : response.body().getResultadoFilmes()) {
                                Filme filme = new Filme();
                                filme.setTipoMidia("serie");
                                Convert.converterClassFilmeSerie(filme, f, adapterSerie);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setTitle("Atenção!");
                        dialog.setMessage("Aconteceu um erro: " + t.getMessage());
                        dialog.setPositiveButton("Ok", null);
                        dialog.show();
                    }
                });
    }

}