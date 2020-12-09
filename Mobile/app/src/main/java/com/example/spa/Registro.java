package com.example.spa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    EditText reg_usuario, reg_senha, reg_csenha, reg_email, editTell;
    Button btn_reg, btn_cancel;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dbHelper = new DBHelper(this);

        reg_usuario= (EditText) findViewById(R.id.txt_reg_usuario);
        reg_senha= (EditText) findViewById(R.id.txt_reg_senha);
        reg_csenha= (EditText) findViewById(R.id.txt_reg_csenha);
        btn_reg = (Button) findViewById(R.id.btn_reg_Enviar);
        reg_email= (EditText) findViewById(R.id.txt_reg_email);
        editTell= (EditText) findViewById(R.id.txt_reg_tell);


        btn_cancel= (Button) findViewById(R.id.btnCancel);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = reg_usuario.getText().toString();
                String senha = reg_senha.getText().toString();
                String csenha = reg_csenha.getText().toString();
                String email = reg_email.getText().toString();
                String tell = editTell.getText().toString();


                if(usuario.equals("")) {
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
                    long res = dbHelper.CriarLogin(usuario, senha,email,tell);
                    if(res>-2){
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        dbHelper.Apagarlogin(usuario);
                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = reg_usuario.getText().toString();
                dbHelper.Apagarlogin(usuario);
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
        return numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
                numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
    }

}