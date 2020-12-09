package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.TextView;

import com.example.spa.R;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        TextView texto = (TextView) findViewById(R.id.txtsobre);

        texto.setText("   A empresa de desenvolvimento de sistemas, Creative Tech, foi criada no meio do ano de 2020, um pouco antes de iniciarmos o desenvolvimento do TCM. Ela foi criada com o propósito de ser uma demonstração da evolução da nossa equipe como um todo, durante o ano, mostrando o quanto conseguimos absorver os conhecimentos passados durante o mesmo. Seu nome foi escolhido com base em tecnologia criativa, este nome foi dado pelo fato que o grupo precisava um nome único, que pudesse ser abreviado facilmente e que remetesse a nossa metodologia de trabalho, traduzido, Creative Tech seria algo como, Tecnologia Criativa.\n");
    }
}