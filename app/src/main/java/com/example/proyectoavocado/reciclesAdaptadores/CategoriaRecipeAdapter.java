package com.example.proyectoavocado.reciclesAdaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Categoria;

import java.util.ArrayList;
import java.util.List;


public class CategoriaRecipeAdapter extends RecyclerView.Adapter<CategoriaRecipeAdapter.ViewHolder> {
    @NonNull
    @Override
    public CategoriaRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaRecipeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /*   private OnCategoriaClickListener listener;
    private Categoria categoriaSeleccionada;

    private List<Categoria> categoriasList;

    public CategoriaRecipeAdapter(List<Categoria> categoriasList) {
        if (categoriasList == null) {
            this.categoriasList = new ArrayList<>(); // Inicializa la lista si es nula
        } else {
            this.categoriasList = categoriasList;
        }
    }

    public void actualizarCategorias(List<Categoria> nuevasCategorias) {
        categoriasList.clear();
        categoriasList.addAll(nuevasCategorias);
        notifyDataSetChanged();
    }


    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);

    private List<Categoria> categorias;
    private OnCategoriaClickListener listener;
    private Categoria categoriaSeleccionada;


    public CategoriaRecipeAdapter(List<Categoria> categorias)
    {
        this.categorias = categorias;
    }


    public void actualizarCategorias(List<Categoria> nuevasCategorias) {
        categorias.clear();
        categorias.addAll(nuevasCategorias);
        notifyDataSetChanged();

    }




    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }


    public void setOnCategoriaClickListener(OnCategoriaClickListener listener) {
        this.listener = listener;
    }

    public Categoria getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public List<Categoria> obtenerCategoriasSeleccionadas() {
        List<Categoria> categoriasSeleccionadas = new ArrayList<>();
        for (Categoria categoria : categoriasList) {


    public List<Categoria> obtenerCategoriasSeleccionadas() {
        List<Categoria> categoriasSeleccionadas = new ArrayList<>();
        for (Categoria categoria : categorias) {
            if (categoria.isSelected()) {
                categoriasSeleccionadas.add(categoria);
            }
        }
        return categoriasSeleccionadas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categoriasList.get(position);
        holder.bind(categoria, listener);
    }


    @Override
    public int getItemCount() {
        return (categoriasList != null) ? categoriasList.size() : 0;
        return categorias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoriaTextView;
        RadioButton checkBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaTextView = itemView.findViewById(R.id.categoria_view);
            checkBox = itemView.findViewById(R.id.categoria_item);
        }


        public void bind(final Categoria categoria, final OnCategoriaClickListener listener) {
            categoriaTextView.setText(categoria.getNombre());
            checkBox.setChecked(categoria.isSelected());


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    categoria.setSelected(isChecked);
                    if (isChecked) {
                        categoriaSeleccionada = categoria;
                    } else {
                        categoriaSeleccionada = null;
                    }
                    if (listener != null) {
                        listener.onCategoriaClick(categoria);
                    }
                }
            });
        }

    }*/
}



