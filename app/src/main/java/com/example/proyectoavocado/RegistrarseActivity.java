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

public class RegistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        //capturo los id de los botones
        ImageButton btnVolver = findViewById(R.id.btn_backInicio);
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir InicioActivity
                Intent intent = new Intent(RegistrarseActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir InicioActivity
                Intent intent = new Intent(RegistrarseActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void llamar(){
        // Importar el recurso y asignarlo a una variable
        String pc_ip = getResources().getString(R.string.pc_ip);
        // Concatenarlo con la url (se los voy a dar hecho)
        String url = "http://" + pc_ip + ":3000/login";


        //Recuerden especificar bien el método que van a usar
    StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Todo lo que esté contenido en el bloque try catch es lo que tienen
                //que modificar de acuerdo a lo que necesiten para su Activity
                try {
                    JSONObject json = new JSONObject(response);
                    String jsonString = json.toString();
                    Log.d("JSON", jsonString);
                    String title = json.getString("nombreCompleto");
                    Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    //Modificar el mensaje para personalizarlo (mensaje para logcat)
                    Log.e("Error en la request", "Error al traer los datos: " + e.getMessage());
                    throw new RuntimeException("Error al traer los datos");
                }
// Todo lo que sigue de acá lo dejan como está
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
        // El “get” que está dentro de la función add es el StringRequest
        // que está más arriba, por si le cambian el nombre
        Volley.newRequestQueue(this).add(get);
    }

}