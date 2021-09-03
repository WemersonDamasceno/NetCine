package com.example.netcine.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netcine.R;
import com.example.netcine.models.Filme;
import com.example.netcine.views.DetalhesFilmeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterFilme extends RecyclerView.Adapter<AdapterFilme.ViewHolderFilme> {
    private ArrayList<Filme> listFilmes;
    private Context getContext;

    public AdapterFilme(Context getContext) {
        listFilmes = new ArrayList<>();
        this.getContext = getContext;
    }

    @NonNull
    @Override
    public ViewHolderFilme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filme, null, false);
        return new ViewHolderFilme(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFilme holder, int position) {
        holder.setDados(listFilmes.get(position));
    }


    @Override
    public int getItemCount() {
        return listFilmes.size();
    }

    public void add(Filme filme) {
        listFilmes.add(filme);
        notifyDataSetChanged();
    }

    public void apagarList() {
        listFilmes = new ArrayList<>();
        notifyDataSetChanged();
    }


    class ViewHolderFilme extends RecyclerView.ViewHolder {
        TextView tituloFilme;
        ImageView imgFilme;


        ViewHolderFilme(@NonNull View itemView) {
            super(itemView);
            tituloFilme = itemView.findViewById(R.id.nomeFilme);
            imgFilme = itemView.findViewById(R.id.imgFilme);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext, DetalhesFilmeActivity.class);
                    intent.putExtra("filme", listFilmes.get(getAdapterPosition()));
                    getContext.startActivity(intent);
                }
            });

        }


        private void setDados(final Filme fi) {
            //setar os dados
            if(fi.getTituloFilme().length() >= 12) {
                String nome = fi.getTituloFilme().substring(0, 12)+"...";
                tituloFilme.setText(nome);
            }else {
                tituloFilme.setText(fi.getTituloFilme());
            }
                Picasso.get().load("https://image.tmdb.org/t/p/w342/" + fi.getUrlPoster()).into(imgFilme);


        }


    }


}