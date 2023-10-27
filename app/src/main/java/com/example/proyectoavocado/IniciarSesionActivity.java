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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;


public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);


        //capturo los id de los botones
        ImageButton btnVolver = findViewById(R.id.btn_volver);
        Button btnIniciarSesion = findViewById(R.id.btn_iniciarsesion);
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir InicioActivity
                Intent intent = new Intent(IniciarSesionActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(IniciarSesionActivity.this, FeedActivity.class);
                startActivity(intent);
                //llamar();
            }
        });


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir registrarseActivity
                //Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseActivity.class);
                //startActivity(intent);
                EditText emailEditText = findViewById(R.id.input_email);
                String email = emailEditText.getText().toString();

                // Guarda el correo electrónico en SharedPreferences después de iniciar sesión
                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();

                // Llama al método para hacer la solicitud de inicio de sesión
                llamar();
            }
        });
    }

private void llamar(){
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