package com.example.spa.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.spa.Adapters.ClienteAdapter;
import com.example.spa.Adapters.ServiceAdapter;
import com.example.spa.Classes.Cliente_Class;
import com.example.spa.Classes.DBHelper;
import com.example.spa.Classes.Pedido_Class;
import com.example.spa.R;
import com.example.spa.helper.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Ver_Servico extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnPesq;
    private EditText editPesq;
    private ServiceAdapter serviceAdapter;
    private List<Pedido_Class> listaPedido = new ArrayList<>();
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver__servico);


        recyclerView = (RecyclerView) findViewById(R.id.recyService);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Pedido_Class pedidoselecionado = listaPedido.get(position);
                        Intent intent = new Intent(Ver_Servico.this, Update_Servico.class);
                        intent.putExtra("pedidoselecionado", pedidoselecionado);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        final Pedido_Class pedidoselecionado = listaPedido.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Ver_Servico.this);
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir o pedido do(a) " + pedidoselecionado.getNome() + "?");


                        dialog.setNegativeButton("Não", null);

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dbHelper = new DBHelper(getApplicationContext());
                                if (dbHelper.deletarPedido(pedidoselecionado)){

                                    criarListagem(null);
                                    alert("Sucessor ao remover o pedido");
                                }
                                else{
                                    alert("Erro ao excluir o pedido");
                                }
                            }
                        });

                        dialog.create();
                        dialog.show();


                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));





    }


    private void criarListagem(String pesquisa){

        dbHelper = new DBHelper(getApplicationContext());
        if(pesquisa != null)
        {
            listaPedido = dbHelper.listarPedido(pesquisa);
        }
        else{
            listaPedido = dbHelper.listarPedido(null);
        }
        dbHelper.close();

        serviceAdapter = new ServiceAdapter(listaPedido);
        //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager( layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(serviceAdapter);

    }
    protected void onStart(){
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
                serviceAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}