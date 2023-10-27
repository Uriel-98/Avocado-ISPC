package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Categoria;
import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;
import com.example.proyectoavocado.reciclesAdaptadores.PasosRecetaRecipeAdapter;

import org.json.JSONArray;
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

    Button btnAgregarCategorias;
    LinearLayout containerCategoriasView;

    private List<Categoria> categoriasSeleccionadas = new ArrayList<>();
    private List<Ingrediente> listaIngredientes = new ArrayList<>();

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

        // Capturar id del botón para agregar ingredientes y llamar al cuadro de diálogo
        Button btnAgregarIngrediente = findViewById(R.id.btn_agregarIngredientes);
        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarIngrediente();
            }
        });

        // Capturar id del botón para agregar paso y llamar al cuadro de diálogo
        Button btnAgregarPaso = findViewById(R.id.btn_agregarPasoDialog);
        btnAgregarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarPaso();
            }
        });

        //capturar el id del boton de agregar categorias para mostrar el dialog
        Button btnAgregarIngredientes = findViewById(R.id.btn_agregarIngredientes);
        btnAgregarIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar el diálogo de categorías aquí
                mostrarDialogoCategorias();
            }
        });

        @SuppressLint("WrongViewCast") Button btnEliminarCategoria = findViewById(R.id.btn_eliminarCategoria);
        btnEliminarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si hay categorías seleccionadas para eliminar
                if (!categoriasSeleccionadas.isEmpty()) {
                    // Crear una lista de IDs de categorías seleccionadas
                    List<Integer> idsCategoriasSeleccionadas = new ArrayList<>();
                    for (Categoria categoria : categoriasSeleccionadas) {
                        idsCategoriasSeleccionadas.add(categoria.getIdCategoria());
                    }

                    // Enviar solicitud al servidor para eliminar las categorías seleccionadas de la receta
                    enviarSolicitudEliminarCategorias(idsCategoriasSeleccionadas);
                } else {
                    // Si no hay categorías seleccionadas, mostrar un mensaje al usuario
                    mostrarMensaje("No hay categorías seleccionadas para eliminar.");
                }
            }
        });

        //Inicializa los RecyclerView
        recyclerViewPasos = findViewById(R.id.recycler_pasos);
        recyclerViewIngredientes = findViewById(R.id.recycler_ingredientes);
        // Inicializa el RecyclerView y el adaptador
        ingredienteAdapter = new IngredienteRecipeAdapter(listaIngredientes, (IngredienteRecipeAdapter.OnItemClickListener) this);
        recyclerViewIngredientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIngredientes.setAdapter(ingredienteAdapter);


        // Crea listas de ejemplo para pasos y ingredientes
        List<Paso> listaPasos = new ArrayList<>();
        listaPasos.add(new Paso("Paso 1", "Descripción del paso 1"));
        listaPasos.add(new Paso("Paso 2", "Descripción del paso 2"));


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

    // Método para mostrar el cuadro de diálogo para agregar ingredientes
        private void mostrarDialogoAgregarIngrediente() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_agregar_ingrediente, null);
            builder.setView(view);

            final EditText editTextNombreIngrediente = view.findViewById(R.id.text_ingrediente);
            Button btnAgregarIngredienteLista = view.findViewById(R.id.btn_agregarIngredienteLista);
            Button btnCloseDialog = view.findViewById(R.id.btn_close_dialog);

            final AlertDialog dialog = builder.create();

            btnAgregarIngredienteLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreIngrediente = editTextNombreIngrediente.getText().toString().trim();
                    if (!nombreIngrediente.isEmpty()) {
                        // Enviar solicitud POST para agregar el ingrediente
                        agregarIngrediente(nombreIngrediente, dialog);
                    } else {
                        mostrarMensaje("Por favor, ingresa un nombre para el ingrediente.");
                    }
                }
            });

            btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    // Método para enviar una solicitud POST y agregar un ingrediente a través de la API REST
    private void agregarIngrediente(final String nombreIngrediente, final AlertDialog dialog) {
        String url = "http://tudominio.com/api/agregarIngrediente";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor si es necesario
                        mostrarMensaje("Ingrediente agregado correctamente");
                        // Cerrar el diálogo después de agregar el ingrediente
                        dialog.dismiss();
                        // Puedes realizar acciones adicionales si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                        mostrarMensaje("Error al agregar el ingrediente. Inténtalo de nuevo más tarde.");
                        Log.e("Error", "Error en la solicitud: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombreIngrediente); // Pasar el nombre del ingrediente al servidor
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }
    public void onDeleteClick(int position) {
        // Obtener el ingrediente en la posición especificada
        Ingrediente ingrediente = listaIngredientes.get(position);

        // Eliminar el ingrediente de la lista
        listaIngredientes.remove(position);

        // Notificar al adaptador que se eliminó un elemento en la posición especificada
        ingredienteAdapter.notifyItemRemoved(position);

        // Aquí puedes realizar acciones adicionales, como eliminar el ingrediente de la base de datos
        // o mostrar un mensaje de eliminación exitosa
    }

    //Dialog para agregar el Paso de la Receta y llamada a la bd
    private void mostrarDialogoAgregarPaso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_agregar_paso, null);
        builder.setView(view);

        final EditText editTextTituloPaso = view.findViewById(R.id.text_tituloPaso);
        final EditText editTextDescripcionPaso = view.findViewById(R.id.text_descripcionPaso);
        Button btnAgregarPaso = view.findViewById(R.id.btn_agregarPasoDialog);
        Button btnCloseDialogPaso = view.findViewById(R.id.btn_close_dialogPaso);

        final AlertDialog dialog = builder.create();

        btnAgregarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloPaso = editTextTituloPaso.getText().toString().trim();
                String descripcionPaso = editTextDescripcionPaso.getText().toString().trim();

                if (!tituloPaso.isEmpty() && !descripcionPaso.isEmpty()) {
                    agregarPaso(tituloPaso, descripcionPaso, dialog);
                } else {
                    mostrarMensaje("Por favor, completa todos los campos.");
                }
            }
        });

        btnCloseDialogPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void agregarPaso(final String tituloPaso, final String descripcionPaso, final AlertDialog dialog) {
        String url = "http://tudominio.com/api/agregarPaso";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor si es necesario
                        mostrarMensaje("Paso agregado correctamente");
                        // Cerrar el diálogo después de agregar el paso
                        dialog.dismiss();
                        // Puedes realizar acciones adicionales si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                        mostrarMensaje("Error al agregar el paso. Inténtalo de nuevo más tarde.");
                        Log.e("Error", "Error en la solicitud: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("titulo", tituloPaso);
                params.put("descripcion", descripcionPaso);
                // Puedes agregar más parámetros según las necesidades de tu API
                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

    //Dialog para categorias
    private void mostrarDialogoCategorias() {
        // URL de la API para obtener la lista de categorías
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://"+ pc_ip +":3000/receta/getCategorias";

        // Hacer una solicitud GET a la API usando Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Lista para almacenar las categorías obtenidas
                    List<Categoria> categoriasList = new ArrayList<>();

                    // Iterar por la respuesta JSON y convertirla en objetos Categoria
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject categoriaJson = response.getJSONObject(i);
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(categoriaJson.getInt("id"));
                        categoria.setNombre(categoriaJson.getString("nombre"));

                        // Agregar la categoría a la lista
                        categoriasList.add(categoria);
                    }

                    // Iterar por las categorías y agregar vistas al LinearLayout
                    for (Categoria categoria : categoriasList) {
                        View categoriaView = LayoutInflater.from(ModificarRecetaActivity.this).inflate(R.layout.categoria_layout, null);
                        TextView categoriaTextView = categoriaView.findViewById(R.id.categoria_view);
                        categoriaTextView.setText(categoria.getNombre());

                        // Configurar el clic en la vista para manejar la selección de categorías
                        categoriaView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Verificar si la categoría ya está seleccionada
                                if (categoriasSeleccionadas.contains(categoria)) {
                                    // Si está seleccionada, quitarla de la lista de seleccionadas
                                    categoriasSeleccionadas.remove(categoria);
                                } else {
                                    // Si no está seleccionada, agregarla a la lista de seleccionadas
                                    categoriasSeleccionadas.add(categoria);
                                }

                                // Actualizar la vista de categorías seleccionadas
                                mostrarCategoriasSeleccionadas();
                            }
                        });

                        // Agregar la vista al LinearLayout
                        containerCategoriasView.addView(categoriaView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Manejar errores al procesar la respuesta JSON
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de la solicitud
                error.printStackTrace();
            }
        });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(ModificarRecetaActivity.this).add(request);
    }

    private void mostrarCategoriasSeleccionadas() {
        // Limpiar el contenedor antes de agregar las vistas
        containerCategoriasView.removeAllViews();

        // Iterar por las categorías seleccionadas y agregar vistas al LinearLayout
        for (Categoria categoria : categoriasSeleccionadas) {
            View categoriaView = LayoutInflater.from(ModificarRecetaActivity.this).inflate(R.layout.categoria_layout, null);
            TextView categoriaTextView = categoriaView.findViewById(R.id.categoria_view);
            categoriaTextView.setText(categoria.getNombre());

            // Agregar la vista al LinearLayout
            containerCategoriasView.addView(categoriaView);
        }
    }

    // Método para enviar solicitud al servidor para eliminar categorías de la receta
    private void enviarSolicitudEliminarCategorias(List<Integer> idsCategoriasSeleccionadas) {
        // URL de la API para eliminar categorías de la receta
        String urlEliminarCategorias = "http://tu_api_para_eliminar_categorias";

        // Crear un objeto JSON con los IDs de las categorías seleccionadas
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArrayIdsCategorias = new JSONArray(idsCategoriasSeleccionadas);
            jsonObject.put("ids_categorias", jsonArrayIdsCategorias);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Enviar una solicitud POST al servidor con el objeto JSON
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlEliminarCategorias, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor si es necesario
                        mostrarMensaje("Categorías eliminadas correctamente.");
                        // Puedes realizar acciones adicionales si es necesario
                        // Por ejemplo, actualizar la vista de categorías
                        // ...
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                        mostrarMensaje("Error al eliminar categorías. Inténtalo de nuevo más tarde.");
                        Log.e("Error", "Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }


    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
