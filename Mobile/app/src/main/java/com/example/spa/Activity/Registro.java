package com.example.spa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spa.Classes.DBHelper;
import com.example.spa.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Registro extends AppCompatActivity {

    EditText reg_usuario, reg_senha, reg_csenha, reg_email, editTell, reg_CPF;
    Button btn_reg, btn_cancel;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dbHelper = new DBHelper(this);

        reg_usuario= (EditText) findViewById(R.id.txt_reg_nome);
        reg_senha= (EditText) findViewById(R.id.txt_reg_senha);
        reg_csenha= (EditText) findViewById(R.id.txt_reg_senhac);
        btn_reg = (Button) findViewById(R.id.btn_reg_Enviar_cli);
        reg_email= (EditText) findViewById(R.id.txt_reg_email);
        editTell= (EditText) findViewById(R.id.txt_reg_tell);
        reg_CPF= (EditText) findViewById(R.id.txt_reg_cpf) ;


        btn_cancel= (Button) findViewById(R.id.btn_up_Cancel);


        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTell, smf);
        editTell.addTextChangedListener(mtw);


        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(reg_CPF, smf2);
        reg_CPF.addTextChangedListener(mtw2);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = reg_usuario.getText().toString();
                String senha = reg_senha.getText().toString();
                String csenha = reg_csenha.getText().toString();
                String email = reg_email.getText().toString();
                String tell = editTell.getText().toString();
                String cpf = reg_CPF.getText().toString();


                if(cpf.equals("")){
                    alert("CPF não inserido");
                }
                if(cpf.length() < 11){
                    alert("CPF não inserido");
                }
                else if(dbHelper.validacaoCPF(cpf)){
                    alert("CPF  já utilizado");
                }
                else if(usuario.equals("")) {
                    alert("Usuario não inserido");
                }
                else if(dbHelper.Validacaousuario(usuario)) {
                    alert("Usuario já registrado");
                }
                else if(tell.equals("")) {
                    alert("Telefone não inserido, tente novamento");
                }
                else if (isTelefone(tell) == false)
                {
                    alert("Telefone inserido incorretamente");
                }
                else if (dbHelper.ValidacaoTell(tell))
                {
                    alert("Telefone já utilizado");
                }
                else if (email.equals(""))
                {
                    alert("Email não inserido");
                }
                else if (dbHelper.Validacaoemail(email)) {
                    alert("Email já utilizado");
                }
                else if (validateEmailFormat(email)  == true)
                {
                    alert("Email escrito incorretamente");
                }
                else if(senha.equals("") || csenha.equals("")){
                    alert("Deve preencher a senha corretamente");
                }
                else if (!senha.equals(csenha)){
                    alert("Deve preencher as duas senhas corretamente");
                }

                else {
                    boolean res = dbHelper.CriarLogin(usuario, senha,email,tell, cpf);
                    if(res == true){
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        alert("Usuário cadastrado");
                        dbHelper.close();
                        startActivity(intent);
                    }
                    else{
                        dbHelper.Apagarlogin(usuario);
                        dbHelper.close();
                        alert("Usuário não cadastrado");
                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = reg_usuario.getText().toString();
                dbHelper.Apagarlogin(usuario);
                dbHelper.close();
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);

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