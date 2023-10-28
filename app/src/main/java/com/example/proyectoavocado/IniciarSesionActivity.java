package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class IniciarSesionActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //capturo los id de los botones
        ImageButton btnVolver = findViewById(R.id.btn_volver);
        Button btnIniciarSesion = findViewById(R.id.btn_iniciarsesion);
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);
        inputEmail = findViewById(R.id.input_email);
        inputContrasena = findViewById(R.id.input_contrasena);

        // Recuperar datos de inicio de sesión desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String correoElectronico = sharedPreferences.getString("email", "");
        String contraseña = sharedPreferences.getString("contrasena", "");
        inputEmail.setText(correoElectronico);
        inputContrasena.setText(contraseña);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesionActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String contrasena = inputContrasena.getText().toString();
                iniciarSesion(email, contrasena);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseActivity.class);
                startActivity(intent);

            }
        });
    }

    private void iniciarSesion(final String email, final String contrasena) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/login";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", contrasena);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest post = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("message");
                            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();

                            // Verificar si la autenticación fue exitosa según la respuesta del servidor
                            boolean autenticacionExitosa = response.getBoolean("success");

                            if (autenticacionExitosa) {
                                // Guardar datos de inicio de sesión en SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.putString("contrasena", contrasena);
                                editor.apply();

                                // Redirigir a FeedActivity
                                Intent intent = new Intent(IniciarSesionActivity.this, FeedActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Log.e("Error en la request", "Error al procesar la respuesta: " + e.getMessage());
                            throw new RuntimeException("Error al procesar la respuesta");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                if (errorMessage != null) {
                    Log.e("Error", errorMessage);
                    Toast.makeText(getApplicationContext(), "Error en el inicio de sesión: " + errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Error", "Mensaje de error nulo");
                }
            }
        });

        Volley.newRequestQueue(this).add(post);
    }
}
/*private void llamar(){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/login";

    StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String jsonString = json.toString();
            Log.d("JSON", jsonString);
            String title = json.getString("nombreCompleto");
           Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
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
}
}*/

