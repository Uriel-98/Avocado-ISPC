package com.example.proyectoavocado.reciclesAdaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.VistaDetalladaActivity;


import java.util.List;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.ViewHolder> {
    private List<Receta> recetas;
    private Context context;

    public RecipeCardAdapter(List<Receta> recetas, Context context) {
        this.recetas = recetas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la tarjeta receta_feed_card.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receta_feed_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardAdapter.ViewHolder holder, int position) {
        Receta receta = recetas.get(position);

        // Configurar las vistas de la tarjeta con datos de la receta
        holder.titulo_receta.setText(receta.getTitulo());
        holder.descripcion_receta.setText(receta.getDescripcion());
        holder.usuario_id_nombre.setText(receta.getCreadoPor());

        // Configurar el clic de la tarjeta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el ID de la receta seleccionada como String
                Integer idDeLaReceta = receta.getIdReceta();
                Log.d("Receta ID", String.valueOf(idDeLaReceta)); // Verifica el valor del ID antes de pasar al Intent

                Intent intent = new Intent(context, VistaDetalladaActivity.class);
                intent.putExtra("receta_id", idDeLaReceta);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView imagen_comida;
        TextView usuario_id_nombre;
        TextView titulo_receta;
        TextView descripcion_receta;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializar las vistas
           //imagen_comida = itemView.findViewById(R.id.imagen_comida);
            usuario_id_nombre = itemView.findViewById(R.id.usuario_id_nombre);
            titulo_receta = itemView.findViewById(R.id.titulo_receta);
            descripcion_receta = itemView.findViewById(R.id.descripcion_receta);
        };
    }
}