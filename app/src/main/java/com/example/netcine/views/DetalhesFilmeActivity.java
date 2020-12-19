package com.example.netcine.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.netcine.R;
import com.example.netcine.models.Filme;

public class DetalhesFilmeActivity extends AppCompatActivity {
Filme detalheMidia;
TextView nomedoFilme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);

        nomedoFilme = findViewById(R.id.nomedofilme);
        try {
            detalheMidia = getIntent().getParcelableExtra("filme");
            nomedoFilme.setText(detalheMidia.getTituloFilme());

        }catch (Exception e){
            Log.i("teste", e.getMessage());
        }



    }
}