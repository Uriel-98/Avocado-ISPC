package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;
import com.example.proyectoavocado.reciclesAdaptadores.PasosRecetaRecipeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarRecetaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPasos;
    private RecyclerView recyclerViewIngredientes;

    private IngredienteRecipeAdapter ingredienteAdapter;
    private PasosRecetaRecipeAdapter pasoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_receta);

        //capturar id del boton para modificar el titulo de la receta y llamada a la funcion en el boton
        ImageButton btnModificarTitulo = findViewById(R.id.btn_edit_tituloReceta);
        btnModificarTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoModificarTitulo();
            }
        });

        //capturar id del boton para modificar la descripcion, dificultad y tiempo de coccion de la receta y llamada a la funcion en el boton
        ImageButton btnEditarDescripcion = findViewById(R.id.btn_editar_descripcion_coccion_dificultad);
        btnEditarDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoEditar();
            }
        });


        //Inicializa los RecyclerView
        recyclerViewPasos = findViewById(R.id.recycler_pasos);
        recyclerViewIngredientes = findViewById(R.id.recycler_ingredientes);

        // Crea listas de ejemplo para pasos y ingredientes
        List<Paso> listaPasos = new ArrayList<>();
        listaPasos.add(new Paso("Paso 1", "Descripción del paso 1"));
        listaPasos.add(new Paso("Paso 2", "Descripción del paso 2"));

        List<Ingrediente> listaIngredientes = new ArrayList<>();
        listaIngredientes.add(new Ingrediente("Ingrediente 1"));
        listaIngredientes.add(new Ingrediente("Ingrediente 2"));

        // Configura el RecyclerView para los pasos
        recyclerViewPasos.setLayoutManager(new LinearLayoutManager(this));
        pasoAdapter = new PasosRecetaRecipeAdapter(listaPasos, new PasosRecetaRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar el paso
                // ...
            }
        });
        recyclerViewPasos.setAdapter(pasoAdapter);

        // Configura el RecyclerView para los ingredientes
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));
        ingredienteAdapter = new IngredienteRecipeAdapter(listaIngredientes, new IngredienteRecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar el ingrediente
                // ...
            }
        });
        recyclerViewIngredientes.setAdapter(ingredienteAdapter);
    }

    //Dialog para moificar el titulo y conecxion a la base de dato
    private void mostrarDialogoModificarTitulo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_modificar_titulo_receta, null);
        builder.setView(view);

        final EditText editTextNuevoTitulo = view.findViewById(R.id.editTextNuevoTitulo);

        builder.setPositiveButton("Modificar Nombre", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoTitulo = editTextNuevoTitulo.getText().toString().trim();
                if (!nuevoTitulo.isEmpty()) {
                    enviarSolicitudActualizarTitulo(nuevoTitulo);
                } else {
                    // Manejar el caso en el que el nuevo título esté vacío
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void enviarSolicitudActualizarTitulo(final String nuevoTitulo) {
        String url = "http://tudominio.com/api/actualizar_titulo_receta";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        mostrarMensaje("Título actualizado correctamente");
                        // Puedes realizar acciones adicionales si es necesario
                    } else {
                        mostrarMensaje("Error al actualizar el título. Inténtalo de nuevo más tarde.");
                        // Muestra un mensaje de error o maneja la situación según tus necesidades
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Manejar el error de análisis JSON si es necesario
                    mostrarMensaje("Error de respuesta del servidor. Inténtalo de nuevo más tarde.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Manejar errores de Volley, como falta de conexión o tiempo de espera agotado
                mostrarMensaje("Error de conexión. Por favor, verifica tu conexión a Internet.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nuevo_titulo", nuevoTitulo);
                // Puedes agregar más parámetros según las necesidades de tu API
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    //dialog para modificar descripcion, nivel de dificultad y tiempo de coccion y conexion a la bd
    private void mostrarDialogoEditar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_editar_descripcion_coccion_dificultad, null);
        builder.setView(view);

        final EditText editTextNuevaDescripcion = view.findViewById(R.id.editTextDescripcion);
        final EditText editTextNuevoTiempoCoccion = view.findViewById(R.id.editTextTiempoCoccion);
        final Spinner spinnerNuevaDificultad = view.findViewById(R.id.spinnerDificultad);

        builder.setPositiveButton("Modificar Receta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevaDescripcion = editTextNuevaDescripcion.getText().toString().trim();
                String nuevoTiempoCoccion = editTextNuevoTiempoCoccion.getText().toString().trim();
                String nuevaDificultad = spinnerNuevaDificultad.getSelectedItem().toString();

                if (!nuevaDescripcion.isEmpty() && !nuevoTiempoCoccion.isEmpty()) {
                    enviarSolicitudActualizarReceta(nuevaDescripcion, nuevoTiempoCoccion, nuevaDificultad);
                } else {
                    // Manejar el caso en el que alguno de los campos esté vacío
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void enviarSolicitudActualizarReceta(final String nuevaDescripcion, final String nuevoTiempoCoccion, final String nuevaDificultad) {
        String url = "http://tudominio.com/api/actualizar_receta";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        mostrarMensaje("Receta actualizada correctamente");
                        // Puedes realizar acciones adicionales si es necesario
                    } else {
                        mostrarMensaje("Error al actualizar la receta. Inténtalo de nuevo más tarde.");
                        // Muestra un mensaje de error o maneja la situación según tus necesidades
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Manejar el error de análisis JSON si es necesario
                    mostrarMensaje("Error de respuesta del servidor. Inténtalo de nuevo más tarde.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Manejar errores de Volley, como falta de conexión o tiempo de espera agotado
                mostrarMensaje("Error de conexión. Por favor, verifica tu conexión a Internet.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nueva_descripcion", nuevaDescripcion);
                params.put("nuevo_tiempo_coccion", nuevoTiempoCoccion);
                params.put("nueva_dificultad", nuevaDificultad);
                // Puedes agregar más parámetros según las necesidades de tu API
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
