package com.example.netcine.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.netcine.R;
import com.google.firebase.auth.FirebaseAuth;

public class PaginaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);


        if(FirebaseAuth.getInstance().getUid() == null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }


    }
}