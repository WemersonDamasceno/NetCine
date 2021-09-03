package com.example.netcine.views.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class SeriesFragment extends Fragment {
    private PopupMenu popupMenu;
    private AdapterFilme adapterSeries;
    private Button btnOrdenarSeries;
    private RecyclerView rvSeries;
    private final String api_key = "6ee08abf792ae460668806133c782b4c";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_series, container, false);

        btnOrdenarSeries = root.findViewById(R.id.btnOrdenarSeries);
        rvSeries = root.findViewById(R.id.rvSeries);

        configRecyclerViewAdapter();

        buscarSeries();

        btnOrdenarSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(getContext(),btnOrdenarSeries);
                popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.semana) {
                            limparAdapter();
                            btnOrdenarSeries.setText("Semana");
                            //OrdenarMes
                            ordenarSeries("week");
                        } else if (item.getItemId() == R.id.dia) {
                            limparAdapter();
                            btnOrdenarSeries.setText("Dia");
                            //OrdenarDia
                            ordenarSeries("day");
                            adapterSeries.notifyDataSetChanged();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        return root;
    }

    private void configRecyclerViewAdapter() {
        adapterSeries = new AdapterFilme(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rvSeries.setLayoutManager(layoutManager);
        rvSeries.setAdapter(adapterSeries);
    }

    private void limparAdapter() {
        adapterSeries.apagarList();
    }

    private void ordenarSeries(String time_window) {
        ApiService.getInstanceRetrofit().getTrending("tv",time_window,api_key)
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {

                            for (FilmesResponse f : response.body().getResultadoFilmes()) {
                                Filme filme = new Filme();

                                filme.setTipoMidia("serie");
                                Convert.converterClassFilmeSerie(filme,f,adapterSeries);


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {

                    }
                });
    }

    private void buscarSeries() {
        ApiService.getInstanceRetrofit().obterSeriesPopulares(api_key)
                .enqueue(new Callback<FilmesResult>() {
                    @Override
                    public void onResponse(Call<FilmesResult> call, Response<FilmesResult> response) {
                        if (response.isSuccessful()) {

                            for (FilmesResponse f : response.body().getResultadoFilmes()) {
                                Filme filme = new Filme();
                                filme.setUrlPoster(f.getUrlImgPoster());
                                filme.setTituloFilme(f.getNomeSerie());
                                filme.setTipoMidia("serie");

                                filme.setNotaFilme(f.getNota());
                                filme.setDataLancamento(f.getLancamento());
                                filme.setIdFilme(f.getIdResponse()+"");
                                filme.setLinguagem(f.getLinguagem());
                                filme.setDescricaoFilme(f.getDescricao());

                                adapterSeries.add(filme);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FilmesResult> call, Throwable t) {

                    }
                });
    }
}