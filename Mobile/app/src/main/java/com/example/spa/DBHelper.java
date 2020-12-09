package com.example.spa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nome="Login_SPA";

    public DBHelper(@Nullable Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE login(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, usuario varchar(100), senha varchar(100),Email VARCHAR(150) NOT NULL, Fone VARCHAR(20) NOT NULL)";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Login;");
        db.execSQL("DROP TABLE IF EXISTS funcionario;");
        onCreate(db);
    }

    public long CriarLogin(String usuario, String senha, String email, String tell){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",0);
        cv.put("usuario",usuario);
        cv.put("senha",senha);
        cv.put("email",email);
        cv.put("tell",tell);
        long result = db.insert("Login",null,cv);
        return result;
    }
    public void Apagarlogin(String usuario)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("login","usuario" + usuario, null);
    }

    public String ValidarLogin(String usuario, String senha){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM login WHERE usuario=? AND senha=?", new String[]{usuario,senha});
        if(c.getCount()>0){
            return "OK";
        }
        else {
            return "ERRO";
        }
    }
    public boolean Validacaousuario(String string){
        //String nome_usuario = "";
        //SQLiteDatabase db = getWritableDatabase();

        //String selectquery =
        //        "SELECT usuario from login  WHERE usuario = " + "'" + string + "'";



       // Cursor c = db.rawQuery(selectquery, null);

       // if( c != null && c.moveToFirst() ){
       //     c.moveToFirst();
       //     nome_usuario = c.getString(c.getColumnIndex("usuario"));
       //     c.close();
       // }

       // StringBuilder conversor = new StringBuilder();
       // conversor.append(nome_usuario);
       // c.close();
       // return conversor.toString();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM login WHERE usuario=?", new String[]{string,string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Validacaoemail(String string){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM login WHERE email=?", new String[]{string,string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean ValidacaoTell(String string) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM login WHERE fone=?", new String[]{string, string});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
