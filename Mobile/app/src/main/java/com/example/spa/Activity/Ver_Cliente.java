package com.example.spa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.spa.Adapters.ClienteAdapter;
import com.example.spa.Classes.Cliente_Class;
import com.example.spa.Classes.DBHelper;
import com.example.spa.R;
import com.example.spa.helper.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Ver_Cliente extends AppCompatActivity {

    DBHelper dbHelper;
    Button btnCancel;
    RecyclerView recyclerView;
    String[] campos = new String[] {"id","Nome","cpf","Email","endereco","fone"};
    Cursor cursor;
    SimpleCursorAdapter ad;
    private List<Cliente_Class> listacliente = new ArrayList<>();
    private ClienteAdapter clienteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver__cliente);
        recyclerView = (RecyclerView) findViewById(R.id.recyCliente);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Cliente_Class clienteSelecionado = listacliente.get(position);

                                Intent intent = new Intent(Ver_Cliente.this, Update_Cliente.class);
                                intent.putExtra("clienteSelecionado", clienteSelecionado);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                final Cliente_Class clienteSelecionado = listacliente.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(Ver_Cliente.this);
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir o(a) cliente " + clienteSelecionado.getNome() + "?");


                                dialog.setNegativeButton("Não", null);

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dbHelper = new DBHelper(getApplicationContext());
                                        if (dbHelper.deletarCliente(clienteSelecionado)){

                                            criarListagem(null);
                                            alert("Sucessor ao remover o(a) cliente");
                                        }
                                        else{
                                            alert("Erro ao excluir o(a) cliente");
                                        }
                                    }
                                });

                                dialog.create();
                                dialog.show();
                            }
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }


    private void criarListagem(String pesquisa){

        dbHelper = new DBHelper(getApplicationContext());
        if(pesquisa != null)
        {
            listacliente = dbHelper.listar(pesquisa);
        }
        else{
            listacliente = dbHelper.listar(null);
        }
        dbHelper.close();
        //
        clienteAdapter = new ClienteAdapter(listacliente);
        //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayout.VERTICAL));

        recyclerView.setAdapter(clienteAdapter);
    }
    protected  void onStart(){
        criarListagem(null);
        super.onStart();
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pesquisa_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clienteAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}