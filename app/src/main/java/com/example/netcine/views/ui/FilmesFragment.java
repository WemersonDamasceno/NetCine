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


public class FilmesFragment extends Fragment {
    private RecyclerView rvFilmes;
    private PopupMenu popupMenu;
    private AdapterFilme adapterFilme;
    private Button btnOrdenar;
    private String api_key = "6ee08abf792ae460668806133c782b4c";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filmes, container, false);

        btnOrdenar = root.findViewById(R.id.btnOrdenar);
        rvFilmes = root.findViewById(R.id.rvFilmes);

        //Configurando grid e adapter
        configRecyclerViewAdapter();


        buscarFilmes();

        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(getContext(), btnOrdenar);
                popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.semana) {
                            limparAdapter();
                            btnOrdenar.setText("Semana");
                            //Ordenar filmes relevantes da semana
                            ordenarFilmes("week");
                        } else if (item.getItemId() == R.id.dia) {
                            limparAdapter();
                            btnOrdenar.setText("Dia");
                            //Ordenar filmes relevantes do dia
                            ordenarFilmes("day");
                            adapterFilme.notifyDataSetChanged();
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
        adapterFilme = new AdapterFilme(getContext());

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        layoutManager1.setReverseLayout(false);
        layoutManager1.setOrientation(RecyclerView.VERTICAL);

        rvFilmes.setLayoutManager(layoutManager1);
        rvFilmes.setAdapter(adapterFilme);
    }

    private void limparAdapter() {
        adapterFilme.apagarList();
    }

    private void ordenarFilmes(String time_window) {
        ApiService.getInstanceRetrofit().getTrending("movie", time_window, api_key)
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

                    }
                });
    }

    private void buscarFilmes() {
        ApiService.getInstanceRetrofit().obterFilmesRecentes(api_key)
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

                    }
                });
    }
}