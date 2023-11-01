package com.example.proyectoavocado.reciclesAdaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Ingrediente;

import java.util.List;

public class IngredienteViewAdapter extends RecyclerView.Adapter<IngredienteViewAdapter.IngredienteViewHolder> {
    private List<Ingrediente> ingredientes;


    public IngredienteViewAdapter(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @NonNull
    @Override
    public IngredienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediente_view, parent, false);
        return new IngredienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredienteViewHolder holder, int position) {
        Ingrediente ingrediente = ingredientes.get(position);
        holder.nombreIngrediente.setText(ingrediente.getNombre());
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    static class IngredienteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreIngrediente;

        IngredienteViewHolder(View itemView) {
            super(itemView);
            nombreIngrediente = itemView.findViewById(R.id.nombre_ingrediente);
        }
    }
}
