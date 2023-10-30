package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        //capturo los id de los botones
        ImageButton btnVolver = findViewById(R.id.btn_backInicio);
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);

        //capturo datos de los edit text
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etUsuario= findViewById(R.id.etUser);
        EditText etPassword = findViewById(R.id.etPassword);

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
                registrarse(etNombre.getText().toString(), etEmail.getText().toString(), etUsuario.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void registrarse(String nombre, String email, String usuario, String password){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/registro";

    StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean success = json.getBoolean("success");
                    String msg = "";

                    if (!success){
                        if (json.has("content")) {
                            JSONArray contentArray = json.getJSONArray("content");

                            for (int i = 0; i < contentArray.length(); i++) {
                                JSONObject contentObject = contentArray.getJSONObject(i); // Obtenemos el primer objeto del arreglo "content"
                                String mensaje = contentObject.getString("msg");
                                msg = mensaje + "\n\n" + msg;
                            }
                        } else {
                            msg = json.getString("message");
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarseActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage(msg);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    } else {

                        msg = json.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarseActivity.this);
                        builder.setMessage(msg);
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegistrarseActivity.this, InicioActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
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
        })

    {
        protected Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("nombreCompleto", nombre);
            params.put("email", email);
            params.put("usuario", usuario);
            params.put("password", password);
            return params;
        }
    };
        // El “get” que está dentro de la función add es el StringRequest
        // que está más arriba, por si le cambian el nombre
        Volley.newRequestQueue(this).add(post);
    }

}