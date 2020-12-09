package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spa.R;

public class Menu_Service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__service);


        Button btnVer = (Button) findViewById(R.id.btnVerservico);
        Button btnCadastrarVer = (Button) findViewById(R.id.btnCadastrarSer);
        Button btnRel = (Button) findViewById(R.id.btnRelatorio) ;

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Service.this, Ver_Servico.class);
                startActivity(intent);
            }
        });
        btnCadastrarVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Service.this, Cadastrar_Servico.class);
                startActivity(intent);
            }
        });
        btnRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Service.this, Relatorio.class);
                startActivity(intent);
            }
        });

    }
}