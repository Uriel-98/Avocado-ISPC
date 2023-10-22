package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ModificarRecetaActivity extends AppCompatActivity {

    private ListView listViewIngredientes;
    private IngredienteRecipeAdapter ingredienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_receta);

        // Obtén la referencia del ListView
        listViewIngredientes = findViewById(R.id.list_ingredientes);

        // Obtén la lista de ingredientes desde tu fuente de datos (por ejemplo, base de datos o API)
        List<Ingrediente> listaIngredientes = obtenerIngredientesDeTuFuenteDeDatos();

        // Crea el adaptador y asígnalo al ListView
        ingredienteAdapter = new IngredienteRecipeAdapter(this, listaIngredientes);
        listViewIngredientes.setAdapter(ingredienteAdapter);
    }

    // Método para obtener la lista de ingredientes desde tu fuente de datos
    private List<Ingrediente> obtenerIngredientesDeTuFuenteDeDatos() {
        // Aquí debes obtener los datos de tu fuente de datos (base de datos, API, etc.)
        // y devolver una lista de objetos Ingrediente.
        // Por ahora, crea una lista de ejemplo para propósitos de demostración.
        List<Ingrediente> listaIngredientes = new ArrayList<>();
        listaIngredientes.add(new Ingrediente("Ingrediente 1"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));
        // Agrega más ingredientes si es necesario.

        return listaIngredientes;
    }
}