package com.example.proyectoavocado.reciclesAdaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.VistaDetalladaActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PerfilRecipeCardAdapter extends RecyclerView.Adapter<PerfilRecipeCardAdapter.ViewHolder> {
    private List<Receta> recetas;
    private Context context;

    public PerfilRecipeCardAdapter(List<Receta> recetas, Context context) {
        this.recetas = recetas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la tarjeta receta_perfil_card.xml para las tarjetas de perfil
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receta_perfil_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receta receta = recetas.get(position);

        // Configurar las vistas de la tarjeta con datos de la receta
        holder.titulo_receta_perfil.setText(receta.getTitulo());

        // Cargar la imagen usando Picasso (o la biblioteca que prefieras)
        Picasso.get().load(receta.getImagen()).into(holder.img_receta);

        // Configurar el clic de la tarjeta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir VistaDetalladaActivity y pasar datos de la receta
                Intent intent = new Intent(context, VistaDetalladaActivity.class);
                intent.putExtra("receta", receta);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_receta;
        TextView titulo_receta_perfil;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializar las vistas
            img_receta = itemView.findViewById(R.id.img_receta);
            titulo_receta_perfil = itemView.findViewById(R.id.titulo_receta_perfil);
        }
    }
}
