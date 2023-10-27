package com.example.proyectoavocado;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarPerfilActivity extends AppCompatActivity {

    private Dialog dialog; // Usar Dialog en lugar de AlertDialog


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        //capturo los id de los botones
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);
        ImageButton btnBackPerfil = findViewById(R.id.btn_backPerfil);
        Button btnEliminarCuenta = findViewById(R.id.btn_eliminarCuenta);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(ModificarPerfilActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(ModificarPerfilActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(ModificarPerfilActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(ModificarPerfilActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogEliminarCuenta();
            }
        });

        btnBackPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(ModificarPerfilActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarDialogEliminarCuenta() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_eliminar_cuenta);

        // captura los id de los elementos dentro del diálogo
        Button positiveButton = dialog.findViewById(R.id.btn_aceptar);
        Button negativeButton = dialog.findViewById(R.id.btn_cancelar);

        // Configurar los clics de los botones dentro del diálogo
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra el diálogo
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementa aquí la lógica para eliminar la cuenta
                eliminarCuenta();
                // Cierra el diálogo
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        // Asegúrate de que dialog no sea nulo antes de mostrarlo
        if (dialog != null) {
            dialog.show();
        }
    }

    private void eliminarCuenta() {
        // Obtener el correo electrónico del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", null);
        //String userEmail = "pedro@example.com";

        if (userEmail != null) {
            // El correo electrónico del usuario está disponible, puedes enviar la solicitud para eliminar la cuenta

            String pc_ip = getResources().getString(R.string.pc_ip);
            String url = "http://" + pc_ip + ":3000/usuario/eliminar?_method=DELETE";

            // Crea un objeto JSON con el correo electrónico del usuario
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("email", userEmail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Crea una solicitud POST con el cuerpo JSON
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Cuenta eliminada con éxito
                            Toast.makeText(getApplicationContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();

                            // Limpiar datos de sesión (cerrar sesión)
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("email"); // Elimina el correo electrónico u otros datos de sesión que estés almacenando
                            editor.apply();

                            // Redirigir a la actividad de inicio
                            Intent intent = new Intent(ModificarPerfilActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish(); // Cierra la actividad actual para evitar volver atrás
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Error al eliminar la cuenta
                            Toast.makeText(getApplicationContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                            // Log de errores
                            Log.e("Error", "Error al eliminar la cuenta: " + error.getMessage());
                        }
                    });

            // Agregar la solicitud a la cola de solicitudes
            Volley.newRequestQueue(this).add(postRequest);
        } else {
            // El correo electrónico del usuario no está disponible en SharedPreferences, muestra un mensaje de error o maneja la situación como desees
            Toast.makeText(getApplicationContext(), "Error mail no existe", Toast.LENGTH_SHORT).show();
        }
    }
}