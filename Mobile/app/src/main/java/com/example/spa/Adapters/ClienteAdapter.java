package com.example.spa.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa.Classes.Cliente_Class;
import com.example.spa.R;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> implements Filterable {

    private List<Cliente_Class> listaCliente;
    private List<Cliente_Class> listaClienteFull;

    public ClienteAdapter(List<Cliente_Class> lista) {

        this.listaCliente = lista;
        listaClienteFull = new ArrayList<>(listaCliente);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_cliente, parent, false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Cliente_Class cliente = listaCliente.get(position);

        holder.nome.setText(cliente.getNome());
        holder.email.setText(cliente.getEmail());
        holder.endereco.setText(cliente.getEndereco());
        holder.tell.setText(cliente.getTell());

    }
    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nome;
        TextView email;
        TextView endereco;
        TextView tell;

        public MyViewHolder(View itemView){

            super(itemView);

            nome = itemView.findViewById(R.id.nome_cliente);
            email = itemView.findViewById(R.id.email_cliente);
            endereco = itemView.findViewById(R.id.endereco_cliente);
            tell = itemView.findViewById(R.id.tell_cliente);

        }
    }

    @Override
    public int getItemCount() {
        return this.listaCliente.size();
    }

    @Override
    public Filter getFilter() {
        return (Filter) listaFilter;
    }

    private  Filter listaFilter = new Filter() {
     @Override
     protected FilterResults performFiltering(CharSequence constraint) {
         List<Cliente_Class> filteredList = new ArrayList<>();
         if (constraint == null || constraint.length() == 00) {
             filteredList.addAll(listaClienteFull);
         }else{
             String filterPattern = constraint.toString().toLowerCase().trim();
             for(Cliente_Class item : listaClienteFull){
                 if(item.getNome().toLowerCase().contains(filterPattern)){
                     filteredList.add(item);
                 }
             }
         }
         FilterResults results = new FilterResults();
         results.values = filteredList;
         return results;
     }
     @Override
     protected void publishResults(CharSequence constraint, FilterResults results) {
         listaCliente.clear();
         listaCliente.addAll((List) results.values);
         notifyDataSetChanged();

      }
   };




}
