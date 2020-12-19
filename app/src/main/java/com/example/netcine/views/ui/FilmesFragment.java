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


public class FilmesFragment extends Fragment {
    RecyclerView rvFilmes;
    AdapterFilme adapterFilme;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filmes, container, false);

        rvFilmes = root.findViewById(R.id.rvFilmes);
        adapterFilme = new AdapterFilme(getContext());





        return root;
    }
}