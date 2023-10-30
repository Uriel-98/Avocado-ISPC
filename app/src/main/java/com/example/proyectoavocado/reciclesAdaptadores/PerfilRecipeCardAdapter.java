package com.example.proyectoavocado.reciclesAdaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

    //cuando toque tengo que mandar el id de la receta en el intent
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receta receta = recetas.get(position);

        // Configurar las vistas de la tarjeta con datos de la receta
        holder.titulo_receta_perfil.setText(receta.getTitulo());

        // Cargar la imagen usando Picasso (o la biblioteca que prefieras)
        //Picasso.get().load(receta.getImagen()).into(holder.img_receta);
        String imagen = receta.getImagen();
        Log.d("IMAGEN RECETA", imagen);
        if(imagen != null && imagen != "null"){
            byte[] decodedString = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            holder.img_receta.setImageBitmap(decodedImage);
        }
        // Configurar el clic de la tarjeta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir VistaDetalladaActivity y pasar datos de la receta

                Intent intent = new Intent(context, VistaDetalladaActivity.class);
                intent.putExtra("receta", receta);
                intent.putExtra("receta_id", receta.getIdReceta());
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
