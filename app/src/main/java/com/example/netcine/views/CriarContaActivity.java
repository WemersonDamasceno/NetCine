package com.example.netcine.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.netcine.R;
import com.example.netcine.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class CriarContaActivity extends AppCompatActivity {
    EditText edNomeNovo;
    EditText edEmailNovo;
    EditText edSenhaNovo;
    Button btnEntrarNovo;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);


        edNomeNovo = findViewById(R.id.edNomeNovo);
        edEmailNovo = findViewById(R.id.edEmailNovo);
        edSenhaNovo = findViewById(R.id.edSenhaNovo);
        btnEntrarNovo = findViewById(R.id.btnEntrarNovo);
        progressDialog = new ProgressDialog(CriarContaActivity.this);


        btnEntrarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCamposVazios();
            }
        });






    }

    private void verificarCamposVazios() {
        if(edSenhaNovo.getText().toString().equals("") ||
                edEmailNovo.getText().toString().equals("") ||
                edNomeNovo.getText().toString().equals("")){
            if(edNomeNovo.getText().toString().equals("")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CriarContaActivity.this);
                alertDialog.setTitle("Atenção!");
                alertDialog.setMessage("O campo nome deve ser preenchido!");
                alertDialog.setPositiveButton("Ok",null);
                alertDialog.show();
            }
            if(edEmailNovo.getText().toString().equals("")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CriarContaActivity.this);
                alertDialog.setTitle("Atenção!");
                alertDialog.setMessage("O campo email deve ser preenchido!");
                alertDialog.setPositiveButton("Ok",null);
                alertDialog.show();
            }
            if(edSenhaNovo.getText().toString().equals("")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CriarContaActivity.this);
                alertDialog.setTitle("Atenção!");
                alertDialog.setMessage("O campo senha deve ser preenchido!");
                alertDialog.setPositiveButton("Ok",null);
                alertDialog.show();
            }
        }else {
            progressDialog.setMessage("Aguarde um momento...");
            progressDialog.show();
            conferirDados();
        }
    }

    private void conferirDados() {
        String nome = edNomeNovo.getText().toString();
        String email = edEmailNovo.getText().toString();
        String senha = edSenhaNovo.getText().toString();

        final User usuario = new User();
        usuario.setNomeUser(nome);
        usuario.setEmailUser(email);
        usuario.setIdUser(FirebaseAuth.getInstance().getUid());

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i("teste","Atentificação bem sucedida.");
                        //salvar usuario no banco
                        salvarUsuarioFB(usuario);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CriarContaActivity.this);
                alertDialog.setTitle("Ocorreu um erro!");
                alertDialog.setMessage(e.getMessage());
                alertDialog.setPositiveButton("Ok",null);
                alertDialog.show();

            }
        });


    }

    private void salvarUsuarioFB(User usuario) {
        FirebaseFirestore.getInstance().collection("users/")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste","Sucesso ao salvar usuario no banco.");
                        startActivity(new Intent(CriarContaActivity.this,PaginaInicialActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CriarContaActivity.this);
                alertDialog.setTitle("Ocorreu um erro!");
                alertDialog.setMessage(e.getMessage());
                alertDialog.setPositiveButton("Ok",null);
                alertDialog.show();
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                finish();
            }
        });
    }

}