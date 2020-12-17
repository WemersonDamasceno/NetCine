package com.example.netcine.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netcine.R;
import com.example.netcine.adapters.AdapterFilme;
import com.example.netcine.models.Filme;


public class HomeFragment extends Fragment {
    RecyclerView rvHomeLancamentos;
    RecyclerView rvHomeCinema;
    RecyclerView rvHomeSeriesRecentes;
    AdapterFilme adapterFilme;AdapterFilme serie;AdapterFilme cinema;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rvHomeLancamentos = root.findViewById(R.id.rvHomeLancamentos);
        rvHomeCinema = root.findViewById(R.id.rvHomeCinema);
        rvHomeSeriesRecentes = root.findViewById(R.id.rvHomeSeriesRecentes);

        adapterFilme = new AdapterFilme(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        rvHomeLancamentos.setAdapter(adapterFilme);
        rvHomeLancamentos.setLayoutManager(layoutManager);

        //teste
         serie = new AdapterFilme(getContext());
         cinema = new AdapterFilme(getContext());



        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setReverseLayout(false);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        rvHomeSeriesRecentes.setLayoutManager(layoutManager1);
        rvHomeSeriesRecentes.setAdapter(serie);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setReverseLayout(false);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvHomeCinema.setLayoutManager(layoutManager2);
        rvHomeCinema.setAdapter(cinema);

        buscarFilmesLancamentos();



        return root;
    }

    private void buscarFilmesLancamentos() {
        Filme f1 = new Filme();
        Filme f2 = new Filme();
        Filme f3 = new Filme();
        Filme f4 = new Filme();
        Filme f5 = new Filme();
        Filme f6 = new Filme();
        Filme f7 = new Filme();
        Filme f8 = new Filme();
        Filme f9 = new Filme();
        Filme f10 = new Filme();
        Filme f11 = new Filme();

        adapterFilme.add(f1);
        adapterFilme.notifyDataSetChanged();

        serie.add(f1);serie.add(f2);serie.add(f3);serie.add(f4);serie.add(f5);serie.add(f6);serie.add(f7);
        cinema.add(f1);cinema.add(f2);cinema.add(f3);cinema.add(f4);cinema.add(f5);cinema.add(f6);cinema.add(f7);


        adapterFilme.add(f2);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f3);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f4);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f5);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f6);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f7);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f8);
        adapterFilme.notifyDataSetChanged();

        adapterFilme.add(f9);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f10);
        adapterFilme.notifyDataSetChanged();
        adapterFilme.add(f11);
        adapterFilme.notifyDataSetChanged();
    }
}