package com.example.proyectoavocado.reciclesAdaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Paso;

import java.util.List;

public class PasosRecetaRecipeAdapter extends RecyclerView.Adapter<PasosRecetaRecipeAdapter.PasoViewHolder> {
    private List<Paso> pasos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public PasosRecetaRecipeAdapter(List<Paso> pasos, OnItemClickListener listener) {
        this.pasos = pasos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PasoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pasos_receta, parent, false);
        return new PasoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasoViewHolder holder, int position) {
        Paso paso = pasos.get(position);
        holder.tituloPaso.setText("Paso " + (position + 1));
        holder.descripcionPaso.setText(paso.getDescripcion());

        holder.eliminarButton.setOnClickListener(view -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pasos.size();
    }

    static class PasoViewHolder extends RecyclerView.ViewHolder {
        TextView tituloPaso;
        TextView descripcionPaso;
        ImageButton eliminarButton;

        PasoViewHolder(View itemView) {
            super(itemView);
            tituloPaso = itemView.findViewById(R.id.titulo_pasoReceta);
            descripcionPaso = itemView.findViewById(R.id.descripcion_pasoReceta);
            eliminarButton = itemView.findViewById(R.id.btn_eliminarPaso);
        }
    }
}
