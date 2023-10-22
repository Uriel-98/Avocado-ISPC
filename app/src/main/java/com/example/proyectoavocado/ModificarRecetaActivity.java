package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;
import com.example.proyectoavocado.reciclesAdaptadores.PasosRecetaRecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ModificarRecetaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPasos;
    private RecyclerView recyclerViewIngredientes;

    private IngredienteRecipeAdapter ingredienteAdapter;
    private PasosRecetaRecipeAdapter pasoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_receta);

        //Inicializa los RecyclerView
        recyclerViewPasos = findViewById(R.id.recycler_pasos);
        recyclerViewIngredientes = findViewById(R.id.recycler_ingredientes);

        // Crea listas de ejemplo para pasos y ingredientes
        List<Paso> listaPasos = new ArrayList<>();
        listaPasos.add(new Paso("Paso 1", "Descripción del paso 1"));
        listaPasos.add(new Paso("Paso 2", "Descripción del paso 2"));

        List<Ingrediente> listaIngredientes = new ArrayList<>();
        listaIngredientes.add(new Ingrediente("Ingrediente 1"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));

        // Configura el RecyclerView para los pasos
        recyclerViewPasos.setLayoutManager(new LinearLayoutManager(this));
        pasoAdapter = new PasosRecetaRecipeAdapter(listaPasos, new PasosRecetaRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar el paso
                // ...
            }
        });
        recyclerViewPasos.setAdapter(pasoAdapter);

        // Configura el RecyclerView para los ingredientes
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));
        ingredienteAdapter = new IngredienteRecipeAdapter(listaIngredientes, new IngredienteRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar el ingrediente
                // ...
            }
        });
        recyclerViewIngredientes.setAdapter(ingredienteAdapter);
    }

}
