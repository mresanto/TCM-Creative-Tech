package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spa.R;

public class Menu_Cliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__cliente);


        Button btnGerenciar = (Button) findViewById(R.id.btnGerenciaCli);
        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrarCli);

         btnGerenciar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Menu_Cliente.this,Ver_Cliente.class);
                 startActivity(intent);
             }
         });
         btnCadastrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Menu_Cliente.this,Cadastrar_Cliente.class);
                 startActivity(intent);
             }
         });


    }
}