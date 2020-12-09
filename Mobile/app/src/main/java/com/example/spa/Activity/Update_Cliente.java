package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spa.Classes.Cliente_Class;
import com.example.spa.Classes.Coordenada;
import com.example.spa.Classes.DBHelper;
import com.example.spa.Classes.GeoLocation;
import com.example.spa.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Update_Cliente extends AppCompatActivity {

    EditText up_nome;
    EditText up_email;
    EditText up_endereco;
    EditText up_tell;
    TextView up_cpf, mapa;
    Button up_cad, up_cancel, btn_mapa;
    Cliente_Class clienteAtual;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__cliente);

        up_nome = (EditText) findViewById(R.id.txt_up_nome_cli);
        up_email = (EditText) findViewById(R.id.txt_up_email_cli);
        up_endereco = (EditText) findViewById(R.id.txt_up_endereco_cli);
        up_tell = (EditText) findViewById(R.id.txt_up_tell_cli);
        up_cad = (Button) findViewById(R.id.btn_up_reg_cli);
        up_cancel = (Button) findViewById(R.id.btn_up_Cancel);
        up_cpf = (TextView) findViewById(R.id.txt_up_cpf);
        btn_mapa = (Button) findViewById(R.id.btnMapa);

        mapa = (TextView) findViewById(R.id.txt_mapa);


        clienteAtual = (Cliente_Class) getIntent().getSerializableExtra("clienteSelecionado");
        if (clienteAtual != null) {
            up_nome.setText(clienteAtual.getNome());
            up_email.setText(clienteAtual.getEmail());
            up_endereco.setText(clienteAtual.getEndereco());
            up_tell.setText(clienteAtual.getTell());
            up_cpf.setText(clienteAtual.getCpf());


        }

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(up_tell, smf);
        up_tell.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(up_cpf, smf2);
        up_cpf.addTextChangedListener(mtw2);

        dbHelper = new DBHelper(this);

        up_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                String address =  clienteAtual.getEndereco();
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.getAddress(address, getApplicationContext(), new GeoHandler());

                }
        });
        up_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = up_nome.getText().toString();
                String email = up_email.getText().toString();
                String endereco = up_endereco.getText().toString();
                String tell = up_tell.getText().toString();

                if (nome.equals("")) {
                    alert("Nome não inserido, tente novamente");
                } else if (nome == clienteAtual.getNome()) {
                    if (dbHelper.Validacaonome(nome)) {
                        alert("Nome já registrado");
                    }
                } else if (email.equals("")) {
                    alert("Email não inserido, tente novamento");
                } else if (email == clienteAtual.getEmail()) {
                    if (dbHelper.Validacaoemailcli(email)) {
                        alert("Email já utilizado");
                    }
                } else if (validateEmailFormat(email) == true) {
                    alert("Email escrito incorretamente");
                } else if (endereco.equals("")) {
                    alert("Endereco não inserido, tente novamento");
                } else if (tell.equals("")) {
                    alert("Telefone não inserido, tente novamento");
                } else if (isTelefone(tell) == false) {
                    alert("Telefone inserido incorretamente");
                } else if (email == clienteAtual.getEmail()) {
                    if (dbHelper.ValidacaoTellCli(tell)) {
                        alert("Telefone já inserido ");
                    }
                } else {
                    Cliente_Class cliente1 = new Cliente_Class();
                    cliente1.setNome(nome);
                    cliente1.setEmail(email);
                    cliente1.setEndereco(endereco);
                    cliente1.setTell(tell);
                    cliente1.setId(clienteAtual.getId());
                    cliente1.setCpf(clienteAtual.getCpf());
                    boolean res = dbHelper.UpdateCliente(cliente1);
                    if (res == true) {
                        finish();
                        alert("Cliente cadastrado");
                    } else {
                        alert("Cliente não cadastrado");
                    }

                }
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public boolean isTelefone(String numeroTelefone) {
        return numeroTelefone.matches("\\(\\d+\\d+\\) *\\d{4}\\d? *- *\\d{4}") ||
                numeroTelefone.matches("\\d{11}");
    }
    private class GeoHandler extends Handler{
        public void handleMessage(Message msg){
            double address;
            double latitude;
            double longitude;
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    Coordenada coordenada = new Coordenada();
                    coordenada.setLongitude(bundle.getDouble("addressL"));
                    coordenada.setLatitude(bundle.getDouble("addressL2"));
                    Log.i("MAPA", "Result" + coordenada.getLongitude() + coordenada.getLatitude());

                    Intent intent = new Intent(Update_Cliente.this, MapsActivity.class);
                    intent.putExtra("coordenada", coordenada);
                    startActivity(intent);


                    break;
                default:
                    longitude = 0;
                    latitude = 0;
                    mapa.setText("Problemas para encontrar o local");
                    Log.i("MAPA", "Result" + longitude + latitude);
                    break;


            }

        }
    }

}