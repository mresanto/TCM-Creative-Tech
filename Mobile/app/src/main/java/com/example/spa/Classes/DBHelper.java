package com.example.spa.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.spa.Adapters.ServiceAdapter;
import com.example.spa.Classes.Cliente_Class;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 2;
    private static String nome="Login_SPA";

    public DBHelper(@Nullable Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE login(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,cpf varchar(14) not null, usuario varchar(100) NOT NULL, senha varchar(100) NOT NULL,Email VARCHAR(150) NOT NULL, Fone VARCHAR(20) NOT NULL);";
        String str1 = "CREATE TABLE cliente(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,cpf varchar(14) not null, nome VARCHAR(100) NOT NULL, email VARCHAR(150) NOT NULL, Endereco VARCHAR(150) NOT NULL, Fone VARCHAR(20) NOT NULL);";
        String str2 = "CREATE TABLE pedido(id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_cliente INTEGER NOT NULL, id_login INTEGER, titulo VARCHAR(15) NOT NULL, status INTEGER CHECK(status IN (1,2,3,4)) NOT NULL DEFAULT 1, texto TEXT, site BOOLEAN NOT NULL CHECK (site IN (0,1)) ,desktop BOOLEAN NOT NULL CHECK (desktop IN (0,1)), infra BOOLEAN NOT NULL CHECK (infra IN (0,1)), mobile BOOLEAN NOT NULL CHECK (mobile IN (0,1)), banco BOOLEAN NOT NULL CHECK (banco IN (0,1)),data DATE NOT NULL, FOREIGN KEY(id_cliente) REFERENCES cliente(id),FOREIGN KEY(id_login) REFERENCES login(id));";
        try {
            db.execSQL(str);
            db.execSQL(str1);
            db.execSQL(str2);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOGIN;");
        db.execSQL("DROP TABLE IF EXISTS Cliente;");
        db.execSQL("Drop TABLE IF EXISTS pedido;");
        onCreate(db);
    }
    public boolean CriarLogin(String usuario, String senha, String email, String tell, String CPF){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usuario",usuario);
        cv.put("cpf", CPF);
        cv.put("senha",senha);
        cv.put("email",email);
        cv.put("fone",tell);
        long result = db.insert("Login ",null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean CriarCliente(String nome, String email, String endereco, String tell, String CPF){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome",nome);
        cv.put("cpf", CPF);
        cv.put("email",email);
        cv.put("endereco",endereco);
        cv.put("fone",tell);
        long result= db.insert("cliente",null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean CriarPedido(Pedido_Class pedido){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_cliente", pedido.getId_cliente());
        cv.put("id_login", 1);
        cv.put("titulo", pedido.getTitulo());
        cv.put("texto", pedido.getText());
        cv.put("site",pedido.getSite());
        cv.put("desktop",pedido.getDesktop());
        cv.put("mobile",pedido.getMobile());
        cv.put("infra",pedido.getInfra());
        cv.put("banco",pedido.getBanco());
        cv.put("data",pedido.getDate());
        cv.put("status",1);
        long result= db.insert("pedido",null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public void Apagarlogin(String usuario) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from login where usuario = '"+usuario+"';");
    }
    public boolean deletarPedido(Pedido_Class pedido) {
        Long i = pedido.getId();
        try{
            String[] args = {i.toString()};
            SQLiteDatabase db = getWritableDatabase();
            db.delete("pedido","id=?",args);
        }catch (Exception e){

        }
        return true;
    }
    public boolean deletarCliente(Cliente_Class cliente) {

        Long i = cliente.getId();
        try{
            String[] args = {i.toString()};
            SQLiteDatabase db = getWritableDatabase();
            db.delete("cliente","id=?",args);
        }catch (Exception e){

        }
        return true;
    }
    public boolean ValidarLogin(String usuario, String senha){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM login WHERE cpf=? AND senha=?;", new String[]{usuario,senha});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
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
        Cursor c = db.rawQuery("SELECT * FROM login WHERE usuario=?;", new String[]{string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Validacaonome(String string){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cliente WHERE nome=?;", new String[]{string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Validacaoemail(String email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM login WHERE email=?;", new String[]{email});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean Validacaoemailcli(String email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cliente WHERE email=?;", new String[]{email});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validacaoCPF(String CPF){
        SQLiteDatabase db = getReadableDatabase();
        String tabela = "login";
        Cursor c = db.rawQuery("SELECT * FROM " + tabela + " where CPF=?;", new String[]{CPF});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validacaoAceito(Long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM pedido where id_login= '"+id+"';", null);
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validacaoCPFcli(String CPF){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cliente where CPF=?;", new String[]{CPF});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean ValidacaoTell(String string) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM login WHERE fone=?;", new String[]{string, string});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean ValidacaoTellCli(String string) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM cliente WHERE fone=?;", new String[]{string, string});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Boolean UpdateCliente(@NotNull Cliente_Class cliente){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome",cliente.getNome());
        cv.put("cpf",cliente.getCpf());
        cv.put("email",cliente.getEmail());
        cv.put("endereco",cliente.getEndereco());
        cv.put("fone",cliente.getTell());
        Long i = cliente.getId();
        try{
            String[] args = {i.toString()};
            db.update("cliente", cv,"id=?", args);
            Log.i("INFO", "funciona");
            return true;

        }
        catch (Exception e){
            Log.i("INFO", "não funciona");
            return false;
        }
    }
    public Boolean UpdatePedido(Pedido_Class pedido){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_cliente", pedido.getId_cliente());
        cv.put("id_login", pedido.getId_login());
        cv.put("titulo", pedido.getTitulo());
        cv.put("texto", pedido.getText());
        cv.put("site",pedido.getSite());
        cv.put("desktop",pedido.getDesktop());
        cv.put("mobile",pedido.getMobile());
        cv.put("infra",pedido.getInfra());
        cv.put("banco",pedido.getBanco());
        cv.put("status", pedido.getStatusi());
        Long i = pedido.getId();
        try{
            String[] args = {i.toString()};
            db.update("pedido", cv,"id=?", args);
            Log.i("INFO", "funciona");
            return true;

        }
        catch (Exception e){
            Log.i("INFO", "não funciona");
            return false;
        }
    }
    public List<Cliente_Class> listar(String pesquisa) {

        SQLiteDatabase db = getWritableDatabase();
        List<Cliente_Class> clientes = new ArrayList<>();
        String sql;
        if( pesquisa != null) {
            sql = "SELECT * FROM cliente WHERE nome LIKE '"+ pesquisa +"%' order by nome asc;";
        }
        else{
            sql = "SELECT * FROM cliente ;";
        }
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Cliente_Class cliente = new Cliente_Class();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String email = c.getString(c.getColumnIndex("email"));
            String endereco = c.getString(c.getColumnIndex("Endereco"));
            String fone = c.getString(c.getColumnIndex("Fone"));
            String cpf = c.getString(c.getColumnIndex("cpf"));

            cliente.setId(id);
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setEndereco(endereco);
            cliente.setTell(fone);
            cliente.setCpf(cpf);

            clientes.add(cliente);
        }
        return clientes;
    }
    public List<Pedido_Class> listarPedido(String pesquisa) {

        SQLiteDatabase db = getWritableDatabase();
        List<Pedido_Class> pedidos = new ArrayList<>();
        String sql;
        if( pesquisa != null) {
            sql = "SELECT * FROM pedido WHERE titulo LIKE '"+ pesquisa +"%' order by date(data) DESC;";
        }
        else{
            sql = "SELECT * FROM pedido ;";
        }
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Pedido_Class pedido = new Pedido_Class();

            Long id = c.getLong(c.getColumnIndex("id"));
            Long id_cli = c.getLong(c.getColumnIndex("id_cliente"));
            Long id_log = c.getLong(c.getColumnIndex("id_login"));
            Integer status = c.getInt(c.getColumnIndex("status"));
            String text = c.getString(c.getColumnIndex("texto"));
            String titulo = c.getString(c.getColumnIndex("titulo"));
            String date = c.getString(c.getColumnIndex("data"));

            Integer  site =(c.getInt(c.getColumnIndex("site")));
            Integer  desktop = (c.getInt(c.getColumnIndex("desktop")));
            Integer  mobile = (c.getInt(c.getColumnIndex("mobile")));
            Integer  infra = (c.getInt(c.getColumnIndex("infra")));
            Integer  banco = (c.getInt(c.getColumnIndex("banco")));


            pedido.setId(id);
            pedido.setId_cliente(id_cli);
            pedido.setId_login(id_log);
            switch (status){
                case 1:
                    pedido.setStatus("Pendente");
                    break;
                case 2:
                    pedido.setStatus("Inicializado");
                    break;
                case 3:
                    pedido.setStatus("Finalizado");
                    break;
            }
            pedido.setStatusi(status);
            pedido.setSite(site);
            pedido.setDesktop(desktop);
            pedido.setMobile(mobile);
            pedido.setInfra(infra);
            pedido.setBanco(banco);
            pedido.setText(text);
            pedido.setNome_login(BuscarNomeLog(id_log));
            pedido.setNome(BuscarNome(id_cli));
            pedido.setTitulo(titulo);
            pedido.setDate(date);
            pedidos.add(pedido);
        }
        return pedidos;
    }
    public Long BuscarID(String nome){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM cliente WHERE nome = '"+ nome + "';";
        Cursor c = db.rawQuery(sql, null);
       c.moveToNext();
        long i = c.getLong(c.getColumnIndex("id"));
        return  i;
    }
    public Long BuscarIDLogin(String nome){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM login WHERE usuario = '"+ nome + "';";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        long i = c.getLong(c.getColumnIndex("id"));
        return  i;
    }
    public Long BuscarIDcpf(String cpf){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM login WHERE cpf = '"+ cpf + "';";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        Long i = c.getLong(c.getColumnIndex("id"));
        return  i;
    }
    public String BuscarNome(Long id){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM cliente WHERE id = '"+ id + "';";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        String i = c.getString(c.getColumnIndex("nome"));
        return  i;
    }
    public String BuscarNomeLog(Long id) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM login WHERE id = '" + id + "';";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        String i = c.getString(c.getColumnIndex("usuario"));
        return i;
    }
}
