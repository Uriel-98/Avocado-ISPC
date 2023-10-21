package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //capturo los id de los botones
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(FeedActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(FeedActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(FeedActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        // Inicializar el RecyclerView y el RecipeCardAdapter
        //RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        // Suponiendo que tienes una lista de recetas llamada 'listaRecetas'
        //List<Receta> listaRecetas = obtenerRecetasDesdeAPI(); // Aquí deberías obtener las recetas de tu API

        // Crear el adapter y establecerlo en el RecyclerView EJEMPLO LOOOCAL
        //RecipeCardAdapter adapter = new RecipeCardAdapter(listaRecetas, this);
        //recyclerView.setAdapter(adapter);
        // Crear datos de ejemplo (con imágenes locales de drawable)
        // Crear datos de ejemplo (con imágenes locales de drawable)
        /*List<Receta> listaRecetas = new ArrayList<>();
        listaRecetas.add(new Receta("Receta 1", R.drawable.tiramisu_comida, "descripcion de la comida"));
        listaRecetas.add(new Receta("Receta 2", R.drawable.pancakes_comida, "descripcion de la comida"));*/
        // Agrega más recetas si es necesario
        // Crear un adaptador con la lista de recetas
        //RecipeCardAdapter adapter = new RecipeCardAdapter(listaRecetas, this);
        // Configura el RecyclerView con el adaptador
        /*RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("DEBUG", "Tamaño de listaRecetas: " + listaRecetas.size());*/

    }

    // Método ficticio para obtener las recetas desde tu API
    /* private List<Receta> obtenerRecetasDesdeAPI() {
        // Aquí deberías hacer una solicitud a tu API para obtener la lista de recetas
        // y luego devolver esa lista.
        // Por ahora, solo retornaremos una lista vacía.
        return new ArrayList<>();
    }*/

    String url = "direccion de la API con recetas";

    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
            response -> {
                // Parsear la respuesta JSON y crear la lista de recetas
                List<Receta> listaRecetas = parsearRespuesta(response);

                // Crear un adaptador con la lista de recetas
                RecipeCardAdapter adapter = new RecipeCardAdapter(listaRecetas, this);

                // Configurar el RecyclerView con el adaptador
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            },
            error -> {
                // Manejar errores de la solicitud a la API, si es necesario
            });

    // Agregar la solicitud a la cola de solicitudes de Volley
        VolleySingleton.getInstance(this).addToRequestQueue(request);

}