package com.example.netcine.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netcine.R;
import com.example.netcine.models.Filme;
import com.squareup.picasso.Picasso;

public class DetalhesFilmeActivity extends AppCompatActivity {
Filme detalheMidia;
TextView tvTituloDescricao;
ImageView imgFilmePoster;
TextView tvLinguagem;
TextView tvLancamento;
TextView tvPopularidade;
ImageView ic_favoritarFilme;
TextView tvResumoDescricao;
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
        ic_favoritarFilme = findViewById(R.id.ic_favoritarFilme);
        tvResumoDescricao = findViewById(R.id.tvResumoDescricao);

        try {
            detalheMidia = getIntent().getParcelableExtra("filme");
            tvTituloDescricao.setText(detalheMidia.getTituloFilme());
            setarDados();
        }catch (Exception e){
            Log.i("teste", e.getMessage());
        }

        ic_favoritarFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //favoritar
                Toast.makeText(DetalhesFilmeActivity.this, "Ainda nao funciona", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setarDados() {
        Picasso.get().load("https://image.tmdb.org/t/p/w342/"+detalheMidia.getUrlPoster()).into(imgFilmePoster);
        tvTituloDescricao.setText(detalheMidia.getTituloFilme());
        tvLinguagem.setText(detalheMidia.getLinguagem());
        tvLancamento.setText(detalheMidia.getDataLancamento());
        tvPopularidade.setText(detalheMidia.getNotaFilme()+"");
        tvResumoDescricao.setText(detalheMidia.getDescricaoFilme());
    }
}