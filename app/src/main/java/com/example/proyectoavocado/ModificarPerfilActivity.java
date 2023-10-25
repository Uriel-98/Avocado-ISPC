package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ModificarPerfilActivity extends AppCompatActivity {
    private AlertDialog dialog;

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

        btnBackPerfil.setOnClickListener(new View.OnClickListener() {
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
    }
    private void eliminarCuenta() {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/eliminar_cuenta"; // Asegúrate de tener el endpoint correcto para eliminar cuentas

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Cuenta eliminada con éxito
                Toast.makeText(getApplicationContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();

                // Redirigir a la actividad de inicio
                Intent intent = new Intent(ModificarPerfilActivity.this, InicioActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual para evitar volver atrás
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error al eliminar la cuenta
                Toast.makeText(getApplicationContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
                // Log de errores
                Log.e("Error", "Error al eliminar la cuenta: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(deleteRequest);
    }

    private void eliminarCuenta() {
        // Obtener el correo electrónico del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", null);

        if (userEmail != null) {
            // El correo electrónico del usuario está disponible, puedes enviar la solicitud para eliminar la cuenta

            String pc_ip = getResources().getString(R.string.pc_ip);
            String url = "http://" + pc_ip + ":3000/eliminar_cuenta"; // Asegúrate de tener el endpoint correcto para eliminar cuentas

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Cuenta eliminada con éxito
                            Toast.makeText(getApplicationContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();

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
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    // Parámetros del cuerpo de la solicitud (correo electrónico del usuario)
                    Map<String, String> params = new HashMap<>();
                    params.put("email", userEmail);
                    return params;
                }
            };

            // Agregar la solicitud a la cola de solicitudes
            Volley.newRequestQueue(this).add(postRequest);
        } else {
            // El correo electrónico del usuario no está disponible en SharedPreferences, muestra un mensaje de error o maneja la situación como desees
        }
    }
    private void mostrarDialogEliminarCuenta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflar el diseño personalizado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_eliminar_cuenta, null);
        builder.setView(dialogView);

        // capturo los id de los elementos
        Button positiveButton = dialogView.findViewById(R.id.btn_aceptar);
        Button negativeButton = dialogView.findViewById(R.id.btn_cancelar);

        // Configurar los clics de los botones
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementa aquí la lógica para eliminar la cuenta
                eliminarCuenta();

                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        // Crear y mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}