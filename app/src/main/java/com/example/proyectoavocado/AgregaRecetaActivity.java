package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;
import com.example.proyectoavocado.reciclesAdaptadores.PasosRecetaRecipeAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;



public class AgregaRecetaActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextDescripcion, editTextTiempoCoccion, editTextDificultad;
    private RecyclerView recyclerViewIngredientes, recyclerViewPasos;
    private IngredienteRecipeAdapter ingredienteAdapter;
    private PasosRecetaRecipeAdapter pasosAdapter;
    private List<Ingrediente> ingredientesList;
    private List<Paso> pasosList;

    private String emailUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_receta);

        // Inicializa los componentes
        ImageButton btnBack = findViewById(R.id.btn_back);
        editTextTitulo = findViewById(R.id.text_title);
        editTextDescripcion = findViewById(R.id.text_descripcion);
        editTextTiempoCoccion = findViewById(R.id.text_tiempoCoccion);
        editTextDificultad = findViewById(R.id.text_dificultad);
        recyclerViewIngredientes = findViewById(R.id.recyclerViewIngredientes);
        recyclerViewPasos = findViewById(R.id.recyclerViewPasos);
        Button btnIngredientes = findViewById(R.id.btn_ingredientes);
        Button btnPasos = findViewById(R.id.btn_pasos);
        ImageButton btnConfirm = findViewById(R.id.btn_confirm);
        ImageButton btnClose = findViewById(R.id.btn_close);

        // Inicializa las listas
        ingredientesList = new ArrayList<>();
        pasosList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        emailUsuario = sharedPreferences.getString("email", "");

        // Configura el adaptador de ingredientes
        ingredienteAdapter = new IngredienteRecipeAdapter(ingredientesList, new IngredienteRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                ingredientesList.remove(position);
                ingredienteAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIngredientes.setAdapter(ingredienteAdapter);


        // Configura el adaptador de pasos
        pasosAdapter = new PasosRecetaRecipeAdapter(pasosList, new PasosRecetaRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                pasosList.remove(position);
                pasosAdapter.notifyDataSetChanged();
            }
        });
        recyclerViewPasos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPasos.setAdapter(pasosAdapter);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar y agregar la receta
                if (validarCampos()) {
                    agregarReceta();
                } else {
                    Toast.makeText(AgregaRecetaActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Botón presionado");
                finish(); // Cierra la actividad actual
                Intent intent = new Intent(AgregaRecetaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Botón presionado");
                finish(); // Cierra la actividad actual
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Botón presionado");
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(AgregaRecetaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Botón presionado");
                mostrarDialogoIngredientes();
            }
        });

        btnPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Botón presionado");
                mostrarDialogoPasos();
            }
        });
    }
    private void mostrarDialogoIngredientes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_agregar_ingrediente, null);
        builder.setView(dialogView);

        EditText editTextIngrediente = dialogView.findViewById(R.id.text_ingrediente);
        Button btnAgregarIngrediente = dialogView.findViewById(R.id.btn_agregarIngredienteLista);
        ImageButton btnCerrarDialog = dialogView.findViewById(R.id.btn_close_dialog);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingrediente = editTextIngrediente.getText().toString().trim();
                if (!ingrediente.isEmpty()) {
                    // Agrega el ingrediente a la lista y actualiza el adaptador
                    Ingrediente nuevoIngrediente = new Ingrediente(ingrediente);
                    ingredientesList.add(nuevoIngrediente);
                    ingredienteAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    // Muestra un mensaje de error si el campo está vacío
                    Toast.makeText(AgregaRecetaActivity.this, "Por favor, ingresa un ingrediente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCerrarDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void mostrarDialogoPasos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_agregar_paso, null);
        builder.setView(dialogView);

        EditText editTextTituloPaso = dialogView.findViewById(R.id.text_tituloPaso);
        EditText editTextDescripcionPaso = dialogView.findViewById(R.id.text_descripcionPaso);
        Button btnAgregarPaso = dialogView.findViewById(R.id.btn_agregarPasoDialog);
        ImageButton btnCerrarDialogPaso = dialogView.findViewById(R.id.btn_close_dialogPaso);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnAgregarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloPaso = editTextTituloPaso.getText().toString().trim();
                String descripcionPaso = editTextDescripcionPaso.getText().toString().trim();
                if (!tituloPaso.isEmpty() && !descripcionPaso.isEmpty()) {
                    // Utiliza el constructor de Paso que solo toma titulo y descripcion
                    Paso nuevoPaso = new Paso(tituloPaso, descripcionPaso);
                    pasosList.add(nuevoPaso);
                    pasosAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    // Muestra un mensaje de error si alguno de los campos está vacío
                    Toast.makeText(AgregaRecetaActivity.this, "Por favor, completa todos los campos del paso", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCerrarDialogPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private boolean validarCampos() {
        boolean camposCompletos = true;
        String titulo = editTextTitulo.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String tiempoCoccion = editTextTiempoCoccion.getText().toString().trim();
        String dificultad = editTextDificultad.getText().toString().trim();

        // Validar campos obligatorios y cambiar color del EditText a rojo si están vacíos
        if (titulo.isEmpty()) {
            editTextTitulo.setError("Campo obligatorio");
            camposCompletos = false;
        }
        if (descripcion.isEmpty()) {
            editTextDescripcion.setError("Campo obligatorio");
            camposCompletos = false;
        }
        if (tiempoCoccion.isEmpty()) {
            editTextTiempoCoccion.setError("Campo obligatorio");
            camposCompletos = false;
        }
        if (dificultad.isEmpty()) {
            editTextDificultad.setError("Campo obligatorio");
            camposCompletos = false;
        }

        return camposCompletos && !ingredientesList.isEmpty() && !pasosList.isEmpty();
    }

    private void agregarReceta() {
        // Obtener datos de la receta desde los EditText
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String tiempoCoccion = editTextTiempoCoccion.getText().toString();
        String dificultad = editTextDificultad.getText().toString();

        // Obtener ingredientes y pasos como List<String>
        List<String> ingredientes = new ArrayList<>();
        for (Ingrediente ingrediente : ingredientesList) {
            ingredientes.add(ingrediente.getNombre());
        }

        // Construir la lista de pasos como objetos JSON
        List<JSONObject> pasos = new ArrayList<>();
        for (Paso paso : pasosList) {
            JSONObject pasoObject = new JSONObject();
            try {
                pasoObject.put("titulo", paso.getTitulo());
                pasoObject.put("descripcion", paso.getDescripcion());
                pasos.add(pasoObject);
                Log.d("TAG", "Paso: " + paso.getTitulo() + ", " + paso.getDescripcion());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al crear el objeto JSON del paso", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Crear el objeto JSON para la solicitud
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("email", emailUsuario);
            requestObject.put("titulo", titulo);
            requestObject.put("descripcion", descripcion);
            requestObject.put("tiempoCoccion", tiempoCoccion);
            requestObject.put("dificultad", dificultad);
            requestObject.put("imagen", "SG9sYSwgdGVzdCBkZSBjb25jYXJhIGVuIEJhc2U2NC4=");
            requestObject.put("ingredientes", new JSONArray(ingredientes));
            requestObject.put("pasos", new JSONArray(pasos)); // Agregar la lista de pasos como un JSONArray
            requestObject.put("categorias", null);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear el objeto JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar la solicitud a la API usando Volley
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/agregarReceta/"; // Reemplaza con la URL de tu API
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");

                            if (success) {
                                // La receta se agregó correctamente
                                String mensaje = response.getString("message");
                                Toast.makeText(AgregaRecetaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                // Puedes finalizar la actividad si la receta se agregó con éxito
                                finish();
                            } else {
                                // La receta no se agregó correctamente
                                String errorMensaje = response.getString("message");
                                Toast.makeText(AgregaRecetaActivity.this, "Error: " + errorMensaje, Toast.LENGTH_SHORT).show();
                                // Puedes hacer más cosas aquí según la respuesta de la API
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            Toast.makeText(AgregaRecetaActivity.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                        /* try {
                            boolean success = response.getBoolean("success");

                            if (success) {
                                // La receta se agregó correctamente
                                String mensaje = response.getString("message");
                                Toast.makeText(AgregaRecetaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                                // Puedes finalizar la actividad si la receta se agregó con éxito
                                finish();
                            } else {
                                // La receta no se agregó correctamente
                                String errorMensaje = response.getString("message");
                                Toast.makeText(AgregaRecetaActivity.this, "Error: " + errorMensaje, Toast.LENGTH_SHORT).show();
                                // Puedes hacer más cosas aquí según la respuesta de la API
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Error al procesar la respuesta JSON
                            Toast.makeText(AgregaRecetaActivity.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error al hacer la solicitud a la API
                        String errorMessage = error.getMessage();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            // Error de conexión o timeout
                            Toast.makeText(AgregaRecetaActivity.this, "Error de conexión. Por favor, verifica tu conexión a Internet", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            // Error de autenticación
                            Toast.makeText(AgregaRecetaActivity.this, "Error de autenticación en el servidor", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            // Error del servidor
                            Toast.makeText(AgregaRecetaActivity.this, "Error del servidor. Por favor, inténtalo de nuevo más tarde", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            // Error de red
                            Toast.makeText(AgregaRecetaActivity.this, "Error de red. Por favor, verifica tu conexión a Internet", Toast.LENGTH_SHORT).show();
                        } else {
                            // Otros tipos de error
                            Toast.makeText(AgregaRecetaActivity.this, "Error al agregar la receta: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                        /*new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Error al hacer la solicitud a la API
                            String errorMessage = error.getMessage();
                            // Mostrar el mensaje de error en el Toast
                            Toast.makeText(AgregaRecetaActivity.this, "Error al agregar la receta: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                            });*/
                    }
                });
        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }
}