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
import com.example.spa.Classes.DBHelper;
import com.example.spa.Classes.Pedido_Class;
import com.example.spa.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> implements Filterable {

    private DBHelper dbHelper;
    private  List<Pedido_Class> listaPedidos;
    private List<Pedido_Class> listaPedidosFull;
    public ServiceAdapter(List<Pedido_Class> lista){
        this.listaPedidos = lista;
        listaPedidosFull = new ArrayList<>(listaPedidos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_service, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pedido_Class pedido = listaPedidos.get(position);
        holder.nome.setText(pedido.getNome());
        holder.func.setText(pedido.getNome_login());
        holder.data.setText(pedido.getDate());
        holder.status.setText(pedido.getStatus());


        String dataRecebida = pedido.getDate();
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

        if (pedido.getStatus() == "Inicializado"){
           holder.status.setTextColor(Color.parseColor("#ffa500"));
        }
        if(comparecao <= 0 && pedido.getStatusi() != 3)
        {
            holder.status.setTextColor(Color.parseColor("#ff0000"));
            pedido.setStatusi(4);
            pedido.setStatus("Atrasado");
            holder.status.setText(pedido.getStatus());

        }
        else if(pedido.getStatusi() == 3){
            holder.status.setTextColor(Color.parseColor("#39ff14"));
        }
        else if(pedido.getStatusi() == 1){
            holder.status.setTextColor(Color.parseColor("#808080"));
        }

    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nome;
        TextView func;
        TextView data;
        TextView status;

        public  MyViewHolder(View itemView){
            super(itemView);

            String a;

            nome = itemView.findViewById(R.id.txtnomecliente);
            func = itemView.findViewById(R.id.txtnomefunc);
            data = itemView.findViewById(R.id.txttitulo);
            status = itemView.findViewById(R.id.txtstatusped);




        }
    }

    @Override
    public int getItemCount() {
        return this.listaPedidos.size();
    }

    @Override
    public Filter getFilter() {
        return listaFilter;
    }
    private Filter listaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pedido_Class> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 00) {
                filteredList.addAll(listaPedidosFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Pedido_Class item : listaPedidosFull){
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

            listaPedidos.clear();
            listaPedidos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getFilterStatus(){
        return  listaFilterStatus;
    }
    private Filter listaFilterStatus = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pedido_Class> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 00) {
                filteredList.addAll(listaPedidosFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Pedido_Class item : listaPedidosFull){
                    if(item.getStatus().toLowerCase().contains(filterPattern)){
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

            listaPedidos.clear();
            listaPedidos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
