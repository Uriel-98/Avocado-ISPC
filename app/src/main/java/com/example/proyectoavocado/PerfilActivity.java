package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.reciclesAdaptadores.PerfilRecipeCardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    TextView text_nombre;
    TextView text_nombreusuario;
    ImageView imageViewPerfil;

    ImageButton boton_menuDesplegable;
    SharedPreferences sharedPreferences;

    List<Receta> listaRecetasPerfil;

    Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        self = this;
        //capturo los id de los botones
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);
        Button btnEditar = findViewById(R.id.btn_editar);

        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String emailSp = sharedPreferences.getString("email", "");
        traerDatosPerfil(emailSp);
        traerRecetas(emailSp);



        text_nombre = findViewById(R.id.text_nombre);
        text_nombreusuario = findViewById(R.id.text_nombreusuario);
        imageViewPerfil = findViewById(R.id.imageViewPerfil);
        boton_menuDesplegable = findViewById(R.id.boton_menuDesplegable);


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




    }
   /* private void conexion(){
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
    }*/

    private void traerDatosPerfil(String userEmail){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/usuario/getUsuario/" + userEmail;


        StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONObject content = json.getJSONObject("content");
                    String nombreCompleto = content.getString("nombreCompleto");
                    String usuario =  content.getString("usuario");
                    String imagen = content.getString("imagen");


                    byte[] decodedString = Base64.decode(imagen, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
                    if (decodedImage != null) {
                        // Si no hay errores al decodificar, muestra la imagen
                        imageViewPerfil.setImageBitmap(decodedImage);
                    } else {
                        // Si no se pudo crear el Bitmap, muestra una imagen de perfil por defecto
                        imageViewPerfil.setImageResource(R.drawable.icono_perfil);
                    }

                    text_nombre.setText(nombreCompleto);
                    text_nombreusuario.setText(usuario);



                } catch (JSONException e) {
                    //Modificar el mensaje para personalizarlo (mensaje para logcat)
                    Log.e("Error en la request", "Error al traer los datos: " + e.getMessage());
                    throw new RuntimeException("Error al traer los datos");
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

    private void traerRecetas(String userEmail){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/getRecetasUsuario/" + userEmail ;


        StringRequest post = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String message = json.getString("message");
                    JSONArray content = json.getJSONArray("content");

                    if(content.length() == 0) {
                    // Imprimir "No tienes recetas en algún mensajito
                    }
                    else {
                        listaRecetasPerfil = new ArrayList<>();
                        Log.d("CONTENT", String.valueOf(content));
                        for(int i = 0; i < content.length(); i++){
                            JSONObject el = content.getJSONObject(i);
                            int idReceta = el.getInt("idReceta");
                            String titulo = el.getString("titulo");
                            String imagen = el.getString("imagen");
                            listaRecetasPerfil.add(new Receta(idReceta, titulo, imagen));
                            // Agrega más recetas del perfil si es necesario



                            // Crear un adaptador con la lista de recetas del perfil del usuario
                            PerfilRecipeCardAdapter adapter = new PerfilRecipeCardAdapter(listaRecetasPerfil, self);

                            // Configurar el RecyclerView con el adaptador
                            RecyclerView recyclerView = findViewById(R.id.recyclerPerfil);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(self));
                        }
                    }



                } catch (JSONException e) {
                    //Modificar el mensaje para personalizarlo (mensaje para logcat)
                    Log.e("Error en la request", "Error al traer los datos: " + e.getMessage());
                    throw new RuntimeException("Error al traer los datos");
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
        Volley.newRequestQueue(this).add(post);
    }
}