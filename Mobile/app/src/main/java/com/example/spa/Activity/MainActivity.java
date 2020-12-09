package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spa.Classes.DBHelper;
import com.example.spa.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    TextView tUsuario, tSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEnviar = (Button) findViewById(R.id.btnlogin);
        Button btnRegitro = (Button) findViewById(R.id.btnRegistro);

        tUsuario = (TextView) findViewById(R.id.txtLogin);
        tSenha = (TextView) findViewById(R.id.txtSenha);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(tUsuario, smf2);
        tUsuario.addTextChangedListener(mtw2);


        dbHelper = new DBHelper(this);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String cpf = tUsuario.getText().toString();
                String senha = tSenha.getText().toString();


                if (cpf.equals("")) {
                    alert("Usuario não inserido");
                }
                else if (senha.equals("")) {
                    alert("Senha não inserida");
                }
                else{
                   boolean res = dbHelper.ValidarLogin(cpf,senha);
                    if (res == true){
                        alert("Login bem sucedido");
                        dbHelper.close();

                        SharedPreferences pref = getSharedPreferences("login",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = pref.edit();
                        ed.putString("cpf", cpf);
                        ed.apply();

                        Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                    }else{
                        alert("Login errado, tente novamente");
                        dbHelper.close();
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
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}