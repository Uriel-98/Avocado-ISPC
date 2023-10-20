package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
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
                //Intent intent = new Intent(IniciarSesionActivity.this, FeedActivity.class);
                //startActivity(intent);
                llamar();


            }
        });


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir registrarseActivity
                Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });
    }

private void llamar(){
        String url = "https://fakestoreapi.com/products/1";

    StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            String title = json.getString("title");
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Error", error.getMessage());
        }
    });
    Volley.newRequestQueue(this).add(get);
}
}