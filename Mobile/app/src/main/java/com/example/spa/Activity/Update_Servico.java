package com.example.spa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spa.Classes.DBHelper;
import com.example.spa.Classes.Pedido_Class;
import com.example.spa.R;

public class Update_Servico extends AppCompatActivity {

    private EditText txt_text, txt_titulo,txt_nome, txt_date;
    private CheckBox site, desktop, mobile, infra, banco;
    private Button btn_Ser,up_cancel;
    private DBHelper dbHelper;
    Pedido_Class pedidoAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__servico);


        txt_nome = (EditText) findViewById(R.id.txt_up_nome_ser);
        txt_text = (EditText) findViewById(R.id.txt_up_obs_text);
        txt_titulo = (EditText) findViewById(R.id.txt_up_titulo_ser);
        site = (CheckBox) findViewById(R.id.checkOne_up);
        desktop = (CheckBox) findViewById(R.id.checkTwo_up);
        mobile = (CheckBox) findViewById(R.id.checkThree_up);
        infra = (CheckBox) findViewById(R.id.checkFour_up);
        banco = (CheckBox) findViewById(R.id.checkFive_up);
        btn_Ser = (Button) findViewById(R.id.btn_Enviar_up_Ser);
        txt_date = (EditText) findViewById(R.id.txt_date_up);
        up_cancel = (Button) findViewById(R.id.btn_up_ser_Cancel);



        pedidoAtual = (Pedido_Class) getIntent().getSerializableExtra("pedidoselecionado");
        if(pedidoAtual != null) {

            txt_nome.setText(pedidoAtual.getNome());
            txt_text.setText(pedidoAtual.getText());
            txt_titulo.setText(pedidoAtual.getTitulo());
            txt_date.setText(pedidoAtual.getDate());


            if(pedidoAtual.getSite() == 1)
                site.setChecked(true);
            else
                site.setChecked(false);

            if(pedidoAtual.getDesktop() == 1)
                desktop.setChecked(true);
            else
                desktop.setChecked(false);

            if(pedidoAtual.getMobile() == 1)
                mobile.setChecked(true);
            else
                mobile.setChecked(false);

            if(pedidoAtual.getInfra() == 1)
                infra.setChecked(true);
            else
                infra.setChecked(false);

            if(pedidoAtual.getBanco() == 1)
                banco.setChecked(true);
            else
                banco.setChecked(false);



            if(pedidoAtual.getId_login() != 1 && pedidoAtual.getStatusi() == 2)
                btn_Ser.setText("Finalizar Pedido");
            else if(pedidoAtual.getId_login() != 1 && pedidoAtual.getStatusi() == 3)
                btn_Ser.setVisibility(View.INVISIBLE);
        }

        dbHelper = new DBHelper(this);
        txt_nome.setEnabled(false);

        up_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Pedido_Class pedido = new Pedido_Class();

                String  nome = txt_nome.getText().toString();
                String  texto = txt_text.getText().toString();
                String titulo = txt_titulo.getText().toString();
                String date = txt_date.getText().toString();

                SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);

                String cpf = (prefs.getString("cpf", null));

                if(site.isChecked())
                    pedido.setSite(1);
                else
                    pedido.setSite(0);
                if(desktop.isChecked())
                    pedido.setDesktop(1);
                else
                    pedido.setDesktop(0);
                if(mobile.isChecked())
                    pedido.setMobile(1);
                else
                    pedido.setMobile(0);
                if(infra.isChecked())
                    pedido.setInfra(1);
                else
                    pedido.setInfra(0);
                if(banco.isChecked())
                    pedido.setBanco(1);
                else
                    pedido.setBanco(0);


                if(nome.equals(""))
                    alert("Cliente não inserido");
                else if(dbHelper.Validacaonome(nome) == false)
                    alert("Cliente não encontrado");
                else if(texto.equals(""))
                    alert("O pedido não possui nenhuma observação");
                else{
                    pedido.setNome(nome);
                    pedido.setText(texto);
                    pedido.setTitulo(titulo);
                    pedido.setDate(date);

                    if(pedidoAtual.getStatusi() == 1){
                        pedido.setStatusi(2);
                        pedido.setStatus("Inicializado");
                    }
                    else if(pedidoAtual.getStatusi() == 2){
                        pedido.setStatusi(3);
                        pedido.setStatus("Finalizado");
                    }


                    long a = 2;
                    pedido.setId(pedidoAtual.getId());
                    pedido.setId_login(dbHelper.BuscarIDcpf(cpf));
                    pedido.setId_cliente(dbHelper.BuscarID(nome));

                    AlertDialog.Builder dialog = new AlertDialog.Builder(Update_Servico.this);


                    boolean res = dbHelper.UpdatePedido(pedido);
                    if(res == true){
                        alert("Pedido modificado");
                        dbHelper.close();
                        finish();
                    }
                    else{
                        //dbHelper.Apagarpedido(pedido.getId());
                        dbHelper.close();
                        alert("Pedido não modificado");
                    }
                }

            }
        });

    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}