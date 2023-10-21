package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //capturo los id de los botones
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);
        Button btnEditar = findViewById(R.id.btn_editar);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(PerfilActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(PerfilActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(PerfilActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(PerfilActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir ModificarPerfilActivity
                Intent intent = new Intent(PerfilActivity.this, ModificarPerfilActivity.class);
                startActivity(intent);
            }
        });

        // Crear datos de ejemplo para las recetas del perfil del usuario (con URLs de imágenes y nombres)
        List<Receta> listaRecetasPerfil = new ArrayList<>();
        listaRecetasPerfil.add(new Receta("https://www.clara.es/medio/2021/12/16/que-comer-hoy-ideas_21beeb02_1200x630.jpg", "Receta 1 del Perfil"));
        listaRecetasPerfil.add(new Receta("https://www.clara.es/medio/2021/12/16/que-comer-hoy-ideas_21beeb02_1200x630.jpg", "Receta 2 del Perfil"));
        // Agrega más recetas del perfil si es necesario

        // Crear un adaptador con la lista de recetas del perfil del usuario
        PerfilRecipeCardAdapter adapter = new PerfilRecipeCardAdapter(listaRecetasPerfil, this);

        // Configurar el RecyclerView con el adaptador
        RecyclerView recyclerView = findViewById(R.id.recyclerPerfil); // Asegúrate de tener un RecyclerView con este ID en activity_perfil.xml
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void conexion(){
        // Realizar la solicitud a la API y configurar el RecyclerView con los datos obtenidos
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/perfil"; // Reemplaza con la URL correcta de tu API

        StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsear la respuesta JSON y crear la lista de recetas del perfil
                    List<Receta> listaRecetasPerfil = parsearRespuesta(response);

                    // Crear un adaptador con la lista de recetas del perfil del usuario
                    PerfilRecipeCardAdapter adapter = new PerfilRecipeCardAdapter(listaRecetasPerfil, PerfilActivity.this);

                    // Configurar el RecyclerView con el adaptador
                    RecyclerView recyclerView = findViewById(R.id.recyclerPerfil); // Asegúrate de tener un RecyclerView con este ID en activity_perfil.xml
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(PerfilActivity.this));
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
            String imagenURL = jsonObject.getString("imagen");
            // ... Parsear otros datos si es necesario

            // Crear objeto Receta y agregarlo a la lista
            Receta receta = new Receta(nombre, imagenURL); // Asegúrate de tener el constructor adecuado en tu clase Receta
            listaRecetas.add(receta);
        }

        return listaRecetas;
    }
}