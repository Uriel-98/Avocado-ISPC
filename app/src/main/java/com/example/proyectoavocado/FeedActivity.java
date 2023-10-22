package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.reciclesAdaptadores.RecipeCardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private void conexion(){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/login";

        StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsear la respuesta JSON y crear la lista de recetas
                    List<Receta> listaRecetas = parsearRespuesta(response);

                    // Crear un adaptador con la lista de recetas
                    RecipeCardAdapter adapter = new RecipeCardAdapter(listaRecetas, FeedActivity.this);

                    // Configurar el RecyclerView con el adaptador
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
                } catch (JSONException e) {
                    Log.e("Error en la request", "Error al parsear los datos: " + e.getMessage());
                    throw new RuntimeException("Error al parsear los datos");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if (errorMessage != null) {
                    Log.e("Error", errorMessage);
                } else {
                    Log.e("Error", "Mensaje de error nulo");
                }
            }
        });
        Volley.newRequestQueue(this).add(get);
    }
    private List<Receta> parsearRespuesta(String response) throws JSONException {
        List<Receta> listaRecetas = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // Obtener datos de la receta del objeto JSON y crear un objeto Receta
            String nombre = jsonObject.getString("nombre");
            String imagenURL = jsonObject.getString("imagen"); // Suponiendo que la URL de la imagen está en el campo "imagen"
            String descripcion = jsonObject.getString("descripcion");
            // ... Parsear otros datos si es necesario

            // Crear objeto Receta y agregarlo a la lista
            Receta receta = new Receta(nombre, imagenURL, descripcion);
            listaRecetas.add(receta);
        }

        return listaRecetas;

        //String url = "direccion de la API con recetas";

    /*JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
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
        VolleySingleton.getInstance(this).addToRequestQueue(request);*/
    }
}