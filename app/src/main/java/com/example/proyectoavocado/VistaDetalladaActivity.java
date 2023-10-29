package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteViewAdaptader;
import com.example.proyectoavocado.reciclesAdaptadores.PasoViewAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class VistaDetalladaActivity extends AppCompatActivity {

    private String recetaIdEspecifica;
    private String emailUsuario;
    private TextView tituloReceta;
    private TextView creadoPor;
    private TextView descripcionView;
    private TextView tiempoCoccionView;
    private TextView dificultadView;
    private RecyclerView recyclerIngrediente;
    private RecyclerView recyclerPaso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_detallada);

        //capturo los id de los botones
        ImageButton btnBack = findViewById(R.id.btn_backFeed);
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);
        ImageButton btnMenuReceta = findViewById(R.id.btn_menu_receta);

        // Instanciar las variables de la interfaz de usuario
        tituloReceta = findViewById(R.id.tituloReceta);
        creadoPor = findViewById(R.id.creadoPor);
        descripcionView = findViewById(R.id.descripcionView);
        tiempoCoccionView = findViewById(R.id.coccionView);
        dificultadView = findViewById(R.id.dificultadView);

        // Instanciar los RecyclerView
        recyclerIngrediente = findViewById(R.id.recyclerIngrediente);
        recyclerPaso = findViewById(R.id.recyclerPaso);

        // Configurar el LayoutManager para los RecyclerView
        LinearLayoutManager layoutManagerIngrediente = new LinearLayoutManager(this);
        LinearLayoutManager layoutManagerPaso = new LinearLayoutManager(this);
        recyclerIngrediente.setLayoutManager(layoutManagerIngrediente);
        recyclerPaso.setLayoutManager(layoutManagerPaso);

        int recetaId = getIntent().getIntExtra("receta_id", -1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        btnMenuReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        // Obtén el correo electrónico del usuario logueado de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("nombre_de_tu_shared_preference", Context.MODE_PRIVATE);
        emailUsuario = sharedPreferences.getString("email", "");

        // Luego, en tu método onCreate o donde sea apropiado, asigna el valor a recetaIdEspecifica
        Intent intent = getIntent();
        if (intent.hasExtra("receta_id")) {
            recetaIdEspecifica = intent.getStringExtra("receta_id");
            obtenerDetallesReceta(recetaIdEspecifica);
        } else {
            // Manejar el caso cuando no se proporciona el ID de la receta
            handleError("ID de receta no proporcionado");
            // Finalizar la actividad actual si no hay un ID de receta para validar
            finish();
        }
    }
    @SuppressLint("ResourceType")
    private void showPopupMenu(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customMenuView = getLayoutInflater().inflate(R.layout.menu_receta, null);

        TextView textViewEditar = customMenuView.findViewById(R.id.menu_modificar);
        TextView textViewEliminar = customMenuView.findViewById(R.id.menu_eliminar);

        textViewEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para editar la receta
                // Abre la actividad de edición (ModificarrecetaActivity)
                Intent intentEditar = new Intent(VistaDetalladaActivity.this, ModificarRecetaActivity.class);
                startActivity(intentEditar);
            }
        });

        textViewEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar la receta
                // Elimina la receta de la base de datos u otras operaciones necesarias
                eliminarReceta(emailUsuario, recetaIdEspecifica);
            }
        });

        builder.setView(customMenuView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class RecetasUsuarioRequest {
        private String email;

        public RecetasUsuarioRequest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public class RecetasUsuarioResponse {
        private List<Receta> recetas;

        public List<Receta> getRecetas() {
            return recetas;
        }
    }

    private void obtenerRecetasUsuario(String emailUsuario) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/getRecetasUsuario";

        // Crear el objeto de solicitud
        RecetasUsuarioRequest request = new RecetasUsuarioRequest(emailUsuario);

        try {
            // Crear un objeto JSON para enviar la solicitud
            JSONObject jsonRequest = new JSONObject(new Gson().toJson(request));

            // Crear una solicitud POST
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Procesar la respuesta del servidor
                            RecetasUsuarioResponse recetasResponse = new Gson().fromJson(response.toString(), RecetasUsuarioResponse.class);
                            List<Receta> recetasDelUsuario = recetasResponse.getRecetas();

                            // Validar si la receta específica pertenece al usuario
                            boolean recetaPerteneceAlUsuario = false;

                            for (Receta receta : recetasDelUsuario) {
                                if (receta.getIdReceta().equals(recetaIdEspecifica)) {
                                    recetaPerteneceAlUsuario = true;
                                    break;
                                }
                            }

                            if (recetaPerteneceAlUsuario) {
                                // La receta pertenece al usuario
                                // Muestra el menú
                                findViewById(R.id.btn_menu_receta).setVisibility(View.VISIBLE);
                            } else {
                                // La receta no pertenece al usuario
                                // Oculta el menú
                                findViewById(R.id.btn_menu_receta).setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejar errores de la solicitud
                            handleError("Error en la solicitud: " + error.getMessage());
                        }
                    });

            // Agregar la solicitud a la cola de solicitudes
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            handleError("Error al crear la solicitud: " + e.getMessage());
        }
    }

    private void obtenerDetallesReceta(String recetaId) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/getRecetaById/" + recetaId;

        // Realizar la solicitud GET al servidor para obtener los detalles de la receta por su ID
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta del servidor y actualizar la interfaz de usuario
                        try {
                            // Parsear la respuesta JSON para obtener los detalles de la receta
                            String titulo = response.getString("titulo");
                            String nombreUsuario = response.getString("nombreUsuario");
                            String descripcion = response.getString("descripcion");
                            String tiempoCoccion = response.getString("tiempoCoccion");
                            String dificultad = response.getString("dificultad");

                            // Obtener la lista de ingredientes y pasos de preparación
                            JSONArray ingredientesArray = response.getJSONArray("ingredientes");
                            JSONArray pasosArray = response.getJSONArray("pasos");

                            // Actualizar la interfaz de usuario con los datos de la receta
                            tituloReceta.setText(titulo);
                            creadoPor.setText("@" + nombreUsuario);
                            descripcionView.setText(descripcion);
                            tiempoCoccionView.setText(tiempoCoccion );
                            dificultadView.setText(dificultad);

                            // Parsear y mostrar la lista de ingredientes
                            List<Ingrediente> ingredientesList = new ArrayList<>();
                            for (int i = 0; i < ingredientesArray.length(); i++) {
                                JSONObject ingredienteJson = ingredientesArray.getJSONObject(i);
                                String nombre = ingredienteJson.getString("nombre"); // Reemplaza "nombre" con la clave correcta en tu JSON

                                // Crea un objeto Ingrediente y agrégalo a la lista
                                Ingrediente ingrediente = new Ingrediente(nombre);
                                ingredientesList.add(ingrediente);
                            }

                            // Parsear y mostrar la lista de pasos de preparación
                            List<Paso> pasosList = new ArrayList<>();
                            for (int i = 0; i < pasosArray.length(); i++) {
                                JSONObject pasoJson = pasosArray.getJSONObject(i);
                                String tituloPaso = pasoJson.getString("titulo");
                                String descripcionPaso = pasoJson.getString("descripcion");

                                // Crea un objeto Paso y agrégalo a la lista
                                Paso paso = new Paso(tituloPaso, descripcionPaso);
                                pasosList.add(paso);
                            }

                            // Usar un adapter personalizado para el RecyclerView de pasos
                            PasoViewAdapter pasoAdapter = new PasoViewAdapter(pasosList);
                            recyclerPaso.setAdapter(pasoAdapter);

                            // ... Actualiza otros elementos de la interfaz de usuario con los datos de la receta

                        } catch (JSONException e) {
                            e.printStackTrace();
                            handleError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        handleError("Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void handleError(String errorMessage) {
        // Lógica para manejar el error, por ejemplo, mostrar un mensaje al usuario
        Log.e("Error", errorMessage);
        // También puedes mostrar un Toast o un AlertDialog con el mensaje de error
    }

    private void eliminarReceta(String emailUsuario, String recetaId) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/eliminarReceta/" + recetaId + "?_method=DELETE";

        // Puedes incluir el correo electrónico del usuario en el cuerpo de la solicitud
        // si es necesario para el servidor
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", emailUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
            handleError("Error al construir el cuerpo de la solicitud: " + e.getMessage());
            return;
        }

        // Crear una solicitud POST con el cuerpo de la solicitud y el ID de la receta en la URL
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta del servidor
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");

                            if (success) {
                                // La receta se eliminó correctamente
                                showToast("Receta eliminada: " + message);
                            } else {
                                // No se pudo eliminar la receta
                                showToast("Error al eliminar la receta: " + message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Manejar errores al parsear la respuesta JSON
                            showToast("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        handleError("Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(deleteRequest);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}