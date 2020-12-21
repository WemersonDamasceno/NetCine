package com.example.netcine.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netcine.DAO.FavoritoDAO;
import com.example.netcine.R;
import com.example.netcine.models.Filme;
import com.example.netcine.models.FavoritoFS;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalhesFilmeActivity extends AppCompatActivity {
    Filme detalheMidia;
    TextView tvTituloDescricao;
    ImageView imgFilmePoster;
    TextView tvLinguagem;
    TextView tvLancamento;
    TextView tvPopularidade;
    ImageView favoritar;
    ImageView desfavoritar;
    TextView tvResumoDescricao;
    FavoritoDAO favoritoDAO;

    ArrayList<FavoritoFS> filmesFavoritos;
    final String apiKEY = "6ee08abf792ae460668806133c782b4c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);
        getSupportActionBar().hide();



        imgFilmePoster = findViewById(R.id.imgFilmeDescricao);
        tvTituloDescricao = findViewById(R.id.tvTituloDescricao);
        tvLinguagem = findViewById(R.id.tvLinguagem);
        tvLancamento = findViewById(R.id.tvLancamento);
        tvPopularidade = findViewById(R.id.tvPopularidade);
        favoritar = findViewById(R.id.ic_favoritarFilme);
        desfavoritar = findViewById(R.id.ic_estrelaMarcada);
        tvResumoDescricao = findViewById(R.id.tvResumoDescricao);

        favoritoDAO = new FavoritoDAO(this);

        try {
            detalheMidia = getIntent().getParcelableExtra("filme");
            setarDados();

        } catch (Exception e) {
            Log.i("teste", e.getMessage());
        }

        checarSeFilmeFavorito(detalheMidia);

        favoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //favoritar
                Toast.makeText(DetalhesFilmeActivity.this, "Você marcou como favorito!", Toast.LENGTH_SHORT).show();
                favoritar.setVisibility(View.GONE);
                desfavoritar.setVisibility(View.VISIBLE);
                //ver se é filme e favoritar como filme
                //se nao for filme favoritar como serie
                favoritarFilme();
            }
        });

        desfavoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetalhesFilmeActivity.this, "Você removeu o filme dos favoritos!", Toast.LENGTH_SHORT).show();
                favoritar.setVisibility(View.VISIBLE);
                desfavoritar.setVisibility(View.GONE);
                desfavoritarFilme();
            }
        });

    }

    private void desfavoritarFilme() {
        favoritoDAO.desfavoritarFilmeSQLite(detalheMidia);
        recreate();
    }

    private void favoritarFilme() {
        favoritoDAO.favoritarFilmeSQLite(detalheMidia);
    }

    private void checarSeFilmeFavorito(Filme detalheMidia) {
        //receber todos os favoritos do banco
       filmesFavoritos = favoritoDAO.getListFavoritos();

        for (FavoritoFS favoritoFS : filmesFavoritos) {
            if (favoritoFS.getIdUsuario().equals(FirebaseAuth.getInstance().getUid())) {
                if (favoritoFS.getIdFilme().equals(detalheMidia.getIdFilme())) {
                    favoritar.setVisibility(View.GONE);
                    desfavoritar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setarDados() {
        Picasso.get().load("https://image.tmdb.org/t/p/w342/" + detalheMidia.getUrlPoster()).into(imgFilmePoster);
        tvTituloDescricao.setText(detalheMidia.getTituloFilme());
        tvLinguagem.setText(detalheMidia.getLinguagem());
        tvLancamento.setText(detalheMidia.getDataLancamento());
        tvPopularidade.setText(detalheMidia.getNotaFilme() + "");
        tvResumoDescricao.setText(detalheMidia.getDescricaoFilme());
    }


    @Override
    protected void onDestroy() {
        //voltar com animacao ????
        startActivity(new Intent(this,PaginaInicialActivity.class));
        super.onDestroy();
    }
}