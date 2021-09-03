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
    private EditText edEmailLogin;
    private EditText edSenhaLogin;
    private Button btnLogin;
    private Button btnCriarConta;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        vincularXML();

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

    private void vincularXML() {
        edEmailLogin = findViewById(R.id.edEmailLogin);
        edSenhaLogin = findViewById(R.id.edSenhaLogin);
        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void conferirDados() {
        if(edEmailLogin.getText().toString().equals("") || edSenhaLogin.getText().toString().equals("")) {
            if (edSenhaLogin.getText().toString().equals("")) {
                progressDialog.dismiss();

                alertDialog("senha");
                edSenhaLogin.setError("O campo senha deve ser preenchido...");
            }
            if (edEmailLogin.getText().toString().equals("")) {
                progressDialog.dismiss();
                alertDialog("email");
                edEmailLogin.setError("O cmapo email deve ser preenchido...");
            }
        }else{
            fazerLogin();
        }
    }

    private void alertDialog(String palavra) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Atenção!");
        alertDialog.setMessage("O campo " + palavra + " deve ser preenchido!");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.show();
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