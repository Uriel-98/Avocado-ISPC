package com.example.proyectoavocado.reciclesAdaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Ingrediente;

import java.util.List;

public class IngredienteRecipeAdapter extends RecyclerView.Adapter<IngredienteRecipeAdapter.IngredienteViewHolder> {
    private List<Ingrediente> ingredientes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public IngredienteRecipeAdapter(List<Ingrediente> ingredientes, OnItemClickListener listener) {
        this.ingredientes = ingredientes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediente_receta, parent, false);
        return new IngredienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredienteViewHolder holder, int position) {
        Ingrediente ingrediente = ingredientes.get(position);
        holder.nombreIngrediente.setText(ingrediente.getNombre());
        holder.eliminarButton.setOnClickListener(view -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    static class IngredienteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreIngrediente;
        ImageButton eliminarButton;

        IngredienteViewHolder(View itemView) {
            super(itemView);
            nombreIngrediente = itemView.findViewById(R.id.nombre_ingrediente);
            eliminarButton = itemView.findViewById(R.id.btn_eliminarInrediente);
        }
    }
}
