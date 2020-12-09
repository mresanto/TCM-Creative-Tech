package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spa.Classes.Cliente_Class;
import com.example.spa.Classes.DBHelper;
import com.example.spa.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Cadastrar_Cliente extends AppCompatActivity {

    EditText reg_nome, reg_email, reg_endereco, reg_tell, reg_cpf;
    Button btn_cad, btn_Cancel;
    DBHelper dbHelper;
    private Cliente_Class clienteAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar__cliente);

        reg_nome = (EditText) findViewById(R.id.txt_reg_nome);
        reg_email = (EditText) findViewById(R.id.txt_reg_email);
        reg_endereco = (EditText) findViewById(R.id.txt_reg_endereco);
        reg_tell = (EditText) findViewById(R.id.txt_reg_tell);
        btn_cad = (Button) findViewById(R.id.btn_reg_Enviar_cli);
        reg_cpf = (EditText) findViewById(R.id.txt_reg_cpf);
        btn_Cancel = (Button) findViewById(R.id.btn_up_Cancel);


        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(reg_tell, smf);
        reg_tell.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(reg_cpf, smf2);
        reg_cpf.addTextChangedListener(mtw2);

        dbHelper = new DBHelper(this);

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = reg_nome.getText().toString();
                String email = reg_email.getText().toString();
                String endereco = reg_endereco.getText().toString();
                String tell = reg_tell.getText().toString();
                String cpf = reg_cpf.getText().toString();


                if(cpf.equals("")){
                    alert("CPF não inserido");
                }
                else if(dbHelper.validacaoCPFcli(cpf)){
                    alert("CPF já utilizado");
                }
                else if (nome.equals("")) {
                    alert("Nome não inserido, tente novamente");
                } else if (dbHelper.Validacaonome(nome)) {
                    alert("Nome já registrado");
                } else if (email.equals("")) {
                    alert("Email não inserido, tente novamento");
                } else if (dbHelper.Validacaoemailcli(email)) {
                    alert("Email já utilizado");
                } else if (validateEmailFormat(email) == true) {
                    alert("Email escrito incorretamente");
                } else if (endereco.equals("")) {
                    alert("Endereco não inserido, tente novamento");
                } else if (tell.equals("")) {
                    alert("Telefone não inserido, tente novamento");
                } else if (isTelefone(tell) == false) {
                    alert("Telefone inserido incorretamente");
                } else {
                    boolean res = dbHelper.CriarCliente(nome, email, endereco, tell,cpf);
                    if (res == true) {
                        finish();
                        alert("Cliente cadastrado");
                        dbHelper.close();

                    } else {
                        dbHelper.deletarCliente(clienteAtual);
                        alert("Usuário não cadastrado");
                        dbHelper.close();

                    }
                }
            }
            });
        }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
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
}
