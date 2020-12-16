package com.example.netcine.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.netcine.R;

public class CriarContaActivity extends AppCompatActivity {
    EditText edNomeNovo;
    EditText edEmailNovo;
    EditText edSenhaNovo;
    Button btnEntrarNovo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);


        edNomeNovo = findViewById(R.id.edNomeNovo);
        edEmailNovo = findViewById(R.id.edEmailNovo);
        edSenhaNovo = findViewById(R.id.edSenhaNovo);
        btnEntrarNovo = findViewById(R.id.btnEntrarNovo);







    }
}