package com.example.proyectoavocado.reciclesAdaptadores;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoavocado.R;
import com.example.proyectoavocado.controllers.Categoria;

import java.util.List;

public class CategoriaRecipeAdapter extends RecyclerView.Adapter<CategoriaRecipeAdapter.ViewHolder> {
    private List<Categoria> categorias;
    private OnCategoriaClickListener listener;

    public CategoriaRecipeAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria, boolean isChecked);
    }

    public void setOnCategoriaClickListener(OnCategoriaClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.categoriaTextView.setText(categoria.getNombre());

        // Manejar el clic en el CheckBox
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(categoria.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            categoria.setSelected(isChecked);
            if (listener != null) {
                listener.onCategoriaClick(categoria, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoriaTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaTextView = itemView.findViewById(R.id.categoria_view);
            checkBox = itemView.findViewById(R.id.checkbox_categoria);
        }
    }
}