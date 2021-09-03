package com.example.netcine.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netcine.DAO.FavoritoDAO;
import com.example.netcine.DAO.FavoritoFirebase;
import com.example.netcine.R;
import com.example.netcine.models.Favorito;
import com.example.netcine.models.Filme;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalhesFilmeActivity extends AppCompatActivity {
    private Filme detalheMidia;
    private TextView tvTituloDescricao;
    private ImageView imgFilmePoster;
    private TextView tvLinguagem;
    private TextView tvLancamento;
    private TextView tvPopularidade;
    private ImageView favoritar;
    private ImageView desfavoritar;
    private TextView tvResumoDescricao;
    private FavoritoDAO favoritoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);

        vincunlandoXML();

        favoritoDAO = new FavoritoDAO(this);

        //Recebendo um filme que veio da lista
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

    private void vincunlandoXML() {
        imgFilmePoster = findViewById(R.id.imgFilmeDescricao);
        tvTituloDescricao = findViewById(R.id.tvTituloDescricao);
        tvLinguagem = findViewById(R.id.tvLinguagem);
        tvLancamento = findViewById(R.id.tvLancamento);
        tvPopularidade = findViewById(R.id.tvPopularidade);
        favoritar = findViewById(R.id.ic_favoritarFilme);
        desfavoritar = findViewById(R.id.ic_estrelaMarcada);
        tvResumoDescricao = findViewById(R.id.tvResumoDescricao);
    }


    private void desfavoritarFilme() {
        favoritoDAO.desfavoritarFilmeSQLite(detalheMidia);
    }

    private void favoritarFilme() {
        favoritoDAO.salvarFavoritoSQLite(detalheMidia.getIdFilme(), detalheMidia.getTipoMidia());
        Favorito fs = new Favorito(FirebaseAuth.getInstance().getUid(), detalheMidia.getIdFilme(), detalheMidia.getTipoMidia());

        FavoritoFirebase.adicionarFavorito(fs);

    }

    private void checarSeFilmeFavorito(Filme detalheMidia) {
        //receber todos os favoritos do banco
        ArrayList<Favorito> filmesFavoritos = favoritoDAO.getListFavoritos();

        for (Favorito favorito : filmesFavoritos) {
            if (favorito.getIdUsuario().equals(FirebaseAuth.getInstance().getUid())) {
                if (favorito.getIdFilme().equals(detalheMidia.getIdFilme())) {
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
        tvPopularidade.setText(String.valueOf(detalheMidia.getNotaFilme()));
        tvResumoDescricao.setText(detalheMidia.getDescricaoFilme());
    }
}