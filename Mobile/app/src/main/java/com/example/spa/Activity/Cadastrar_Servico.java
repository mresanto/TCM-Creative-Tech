package com.example.spa.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spa.Classes.DBHelper;
import com.example.spa.Classes.Pedido_Class;
import com.example.spa.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cadastrar_Servico extends AppCompatActivity {

    private EditText txt_nome, txt_text, txt_titulo,txt_date;
    private CheckBox one, two, three, four, five;
    private Button btn_Ser,btn_Cancel;
    private DBHelper dbHelper;
    private ArrayList<String> mResult;
    private List<Pedido_Class> pedidocliente = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar__servico);

        dbHelper = new DBHelper(this);


        txt_nome = (EditText) findViewById(R.id.txt_nome_ser);
        txt_text = (EditText) findViewById(R.id.txt_up_obs_text);
        txt_titulo = (EditText) findViewById(R.id.txt_titulo_ser);
        txt_date = (EditText) findViewById(R.id.txt_date);
        one = (CheckBox) findViewById(R.id.checkOne);
        two = (CheckBox) findViewById(R.id.checkTwo);
        three = (CheckBox) (CheckBox) findViewById(R.id.checkThree);
        four = (CheckBox) findViewById(R.id.checkFour);
        five = (CheckBox) findViewById(R.id.checkFive);
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel);


        btn_Ser = (Button) findViewById(R.id.btn_EnviarSer);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(txt_date, smf);
        txt_date.addTextChangedListener(mtw);

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Ser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Pedido_Class pedido = new Pedido_Class();

                String  nome = txt_nome.getText().toString();
                String  texto = txt_text.getText().toString();
                String titulo = txt_titulo.getText().toString();
                String date = txt_date.getText().toString();


                String dataRecebida = date;
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date dataFormatada = null;
                Date data2 = new Date();
                try {
                    dataFormatada = formato.parse(dataRecebida);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date data = new Date(System.currentTimeMillis());
                int comparecao =dataFormatada.compareTo(data2);




                if(one.isChecked())
                   pedido.setSite(1);
               else
                   pedido.setSite(0);
               if(two.isChecked())
                   pedido.setDesktop(1);
               else
                   pedido.setDesktop(0);
               if(three.isChecked())
                   pedido.setMobile(1);
               else
                   pedido.setMobile(0);
               if(four.isChecked())
                   pedido.setInfra(1);
               else
                   pedido.setInfra(0);
               if(five.isChecked())
                   pedido.setBanco(1);
               else
                   pedido.setBanco(0);


               if(nome.equals(""))
                   alert("Cliente não inserido");
               else if(comparecao <= 0){
                   alert("Date menor que a atual");
                }
               else if(dbHelper.Validacaonome(nome) == false)
                   alert("Cliente não encontrado");
               else if(texto.equals(""))
                   alert("O pedido não possui nenhuma observação");
               else{


                   pedido.setNome(nome);
                   pedido.setText(texto);
                   pedido.setTitulo(titulo);
                   pedido.setDate(date);
                  //pedidocliente = dbHelper.Buscarid(nome);
                  //final Pedido_Class pedido2 = pedidocliente.get(1);
                  //pedido.setId_cliente(pedido2.getId_cliente());
                   pedido.setId_cliente(dbHelper.BuscarID(nome));
                       boolean res = dbHelper.CriarPedido(pedido);
                       if(res == true){
                           alert("Pedido cadastrado");
                           dbHelper.close();
                           finish();
                       }
                       else{
                       //dbHelper.Apagarpedido(pedido.getId());
                       dbHelper.close();
                       alert("Pedido não cadastrado");
                   }
               }
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}