package com.example.netcine.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.netcine.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edEmailLogin;
    EditText edSenhaLogin;
    Button btnLogin;
    Button btnCriarConta;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        edEmailLogin = findViewById(R.id.edEmailLogin);
        edSenhaLogin = findViewById(R.id.edSenhaLogin);
        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(LoginActivity.this);

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CriarContaActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Só um momento...");
                progressDialog.show();
                conferirDados();
            }
        });



    }

    private void conferirDados() {
        if(edEmailLogin.getText().toString().equals("") || edSenhaLogin.getText().toString().equals("")) {
            if (edSenhaLogin.getText().toString().equals("")) {
                progressDialog.dismiss();
                //lancar meng
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Atenção!");
                alert.setMessage("O campo senha deve ser preenchido");
                alert.setPositiveButton("Ok", null);
                alert.show();
                edSenhaLogin.setError("O campo senha deve ser preenchido...");
            } else if (edEmailLogin.getText().toString().equals("")) {
                progressDialog.dismiss();
                //lancar mensg
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Atenção!");
                alert.setMessage("O campo email deve ser preenchido");
                alert.setPositiveButton("Ok", null);
                alert.show();
                edEmailLogin.setError("O cmapo email deve ser preenchido...");
            }
        }else{
            fazerLogin();
        }
    }

    private void fazerLogin() {
        String senha = edSenhaLogin.getText().toString();
        String email = edEmailLogin.getText().toString();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(LoginActivity.this,PaginaInicialActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle("Atenção!");
                alert.setMessage(e.getMessage());
                alert.setPositiveButton("Ok", null);
                alert.show();
                Log.i("teste", "Erro login: "+e.getMessage());
            }
        });
    }
}