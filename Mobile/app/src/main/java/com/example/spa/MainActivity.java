package com.example.spa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEnviar = (Button) findViewById(R.id.btnlogin);
        Button btnRegitro = (Button) findViewById(R.id.btnRegistro);
        Button btnForget = (Button) findViewById(R.id.btnForget);

        dbHelper = new DBHelper(this);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tUsuario = (TextView) findViewById(R.id.txtLogin);
                TextView tSenha = (TextView) findViewById(R.id.txtSenha);
                String usuario = tUsuario.getText().toString();
                String senha = tSenha.getText().toString();
                if (usuario.equals("")) {
                    alert("Usuario não inserido corretamente");
                }
                else if (senha.equals("")) {
                    alert("Senha não inserida corretamente");
                }
                else{
                   String res = dbHelper.ValidarLogin(usuario,senha);
                    if (res.equals("OK")){
                        alert("Login OK");
                    }else{
                        alert("Login errado, tente novamente");
                    }

                }
            }
        });
        btnRegitro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}