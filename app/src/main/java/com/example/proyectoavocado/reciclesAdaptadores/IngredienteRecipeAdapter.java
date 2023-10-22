package com.example.proyectoavocado.reciclesAdaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Ingrediente;

import java.util.List;

public class IngredienteRecipeAdapter extends ArrayAdapter<Ingrediente> {
    private Context context;
    private List<Ingrediente> ingredientes;


    public IngredienteRecipeAdapter(Context context, List<Ingrediente> ingredientes) {
        super(context, 0, ingredientes);
        this.context = context;
        this.ingredientes = ingredientes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtén el objeto Ingrediente para esta posición
        Ingrediente ingrediente = getItem(position);

        // Verifica si una vista existente está siendo reutilizada, de lo contrario, infla la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingrediente_receta, parent, false);
        }

        // Asocia los elementos de la vista con los datos del objeto Ingrediente
        TextView nombreIngrediente = convertView.findViewById(R.id.nombre_ingrediente);
        ImageButton eliminarButton = convertView.findViewById(R.id.btn_eliminarInrediente);

        // Configura el texto y los clics de los botones (editar y eliminar)
        nombreIngrediente.setText(ingrediente.getNombre());

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar el ingrediente
                // ...
            }
        });

        return convertView;
    }
}
