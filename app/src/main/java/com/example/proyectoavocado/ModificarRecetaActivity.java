package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectoavocado.controllers.Ingrediente;
import com.example.proyectoavocado.controllers.Paso;
import com.example.proyectoavocado.controllers.Receta;
import com.example.proyectoavocado.reciclesAdaptadores.IngredienteRecipeAdapter;
import com.example.proyectoavocado.reciclesAdaptadores.PasosRecetaRecipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModificarRecetaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIngredientes, recyclerViewPasos;
    private IngredienteRecipeAdapter ingredienteAdapter;
    private PasosRecetaRecipeAdapter pasosAdapter;
    private List<Ingrediente> ingredientesList;
    private List<Paso> pasosList;

    private EditText tituloReceta;
    private EditText editDescripcion;
    private EditText editTiempoCoccion;
    private EditText dificultadReceta;
    private ImageButton btnEditarTitulo;
    private ImageButton btnEditarDescripcionTiempoDificultad;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_receta);

        // Inicializar vistas y obtener correo electrónico del usuario
        // Inicializar vistas
        tituloReceta = findViewById(R.id.titulo_receta);
        editDescripcion = findViewById(R.id.editDescripcion);
        editTiempoCoccion = findViewById(R.id.editTiempo_coccion);
        dificultadReceta = findViewById(R.id.dificultad_receta);
        btnEditarTitulo = findViewById(R.id.btn_edit_tituloReceta);
        btnEditarDescripcionTiempoDificultad = findViewById(R.id.btn_editar_descripcion_coccion_dificultad);

        // Inicializa las listas
        ingredientesList = new ArrayList<>();
        pasosList = new ArrayList<>();

        obtenerDetallesReceta();

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

        btnEditarTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Habilitar la edición del títuloReceta y mostrar un botón de guardar
                habilitarEdicion(tituloReceta, "titulo");
            }
        });

        btnEditarDescripcionTiempoDificultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Habilitar la edición de editDescripcion, editTiempoCoccion y dificultadReceta
                // Mostrar un botón de guardar para guardar los cambios en la API cuando se haga clic en él
                habilitarEdicion(editDescripcion, "descripcion");
                habilitarEdicion(editTiempoCoccion, "tiempoCoccion");
                habilitarEdicion(dificultadReceta, "dificultad");
            }
        });

        // Capturar id del botón para agregar ingredientes y llamar al cuadro de diálogo
        Button btnAgregarIngrediente = findViewById(R.id.btn_agregarIngredientes);
        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoIngredientes();
            }
        });


        // Capturar id del botón para agregar paso y llamar al cuadro de diálogo
        Button btnAgregarPaso = findViewById(R.id.btn_agregarPasoDialog);
        btnAgregarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoPasos();
            }
        });
    }

    private void habilitarEdicion(EditText editText, String campo) {
        // Habilitar la edición del EditText y mostrar un botón de guardar
        editText.setEnabled(true);
        editText.setFocusable(true);
        editText.requestFocus();
        // Mostrar un botón de guardar para guardar los cambios en la API cuando se haga clic en él
        // Puedes implementar esta lógica según tus necesidades
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
                    Toast.makeText(ModificarRecetaActivity.this, "Por favor, ingresa un ingrediente", Toast.LENGTH_SHORT).show();
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
                    // Agrega el paso a la lista y actualiza el adaptador
                    Paso nuevoPaso = new Paso(tituloPaso, descripcionPaso);
                    pasosList.add(nuevoPaso);
                    pasosAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    // Muestra un mensaje de error si alguno de los campos está vacío
                    Toast.makeText(ModificarRecetaActivity.this, "Por favor, completa todos los campos del paso", Toast.LENGTH_SHORT).show();
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

    private String getEmailUsuarioLogueado() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }

    private void obtenerDetallesReceta() {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String email = getEmailUsuarioLogueado(); // Obtén el email del usuario logueado desde SharedPreferences

        String url = "http://" + pc_ip + ":3000/usuario/getUsuario/" + email;

        StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsea la respuesta para obtener los detalles de la receta del usuario
                    Receta receta = parsearRespuestaReceta(response);

                    // Carga los datos de la receta en los EditText correspondientes
                    cargarDatosReceta(receta);
                } catch (JSONException e) {
                    handleError("Error al parsear los datos de la receta: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError("Error en la solicitud: " + error.getMessage());
            }
        });

        // Asegúrate de añadir la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(get);
    }

    private void cargarDatosReceta(Receta receta) {
        // Carga los datos de la receta en los EditText correspondientes
        tituloReceta.setText(receta.getTitulo());
        editDescripcion.setText(receta.getDescripcion());
        editTiempoCoccion.setText(receta.getTiempoCoccion());
        dificultadReceta.setText(receta.getDificultad());

        // Carga los ingredientes en el RecyclerView de ingredientes
        ingredientesList.addAll(receta.getIngredientes());
        ingredienteAdapter.notifyDataSetChanged();

        // Carga los pasos en el RecyclerView de pasos
        pasosList.addAll(receta.getPasos());
        pasosAdapter.notifyDataSetChanged();
    }

    private List<Ingrediente> obtenerListaDeIngredientes(JSONArray jsonArray) throws JSONException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String nombreIngrediente = jsonArray.getString(i);
            Ingrediente ingrediente = new Ingrediente(nombreIngrediente);
            ingredientes.add(ingrediente);
        }
        return ingredientes;
    }

    private List<Paso> obtenerListaDePasos(JSONArray jsonArray) throws JSONException {
        List<Paso> pasos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pasoJson = jsonArray.getJSONObject(i);
            String titulo = pasoJson.getString("titulo");
            String descripcion = pasoJson.getString("descripcion");
            Paso paso = new Paso(titulo, descripcion);
            pasos.add(paso);
        }
        return pasos;
    }

    private Receta parsearRespuestaReceta(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        // Obtén los valores del JSON
        Integer idReceta = jsonObject.getInt("idReceta");
        String titulo = jsonObject.getString("titulo");
        String descripcion = jsonObject.getString("descripcion");
        String tiempoCoccion = jsonObject.getString("tiempoCoccion");
        String dificultad = jsonObject.getString("dificultad");

        // Obtén la lista de ingredientes como lista de objetos Ingrediente
        List<Ingrediente> ingredientes = obtenerListaDeIngredientes(jsonObject.getJSONArray("ingredientes"));

        // Obtén la lista de pasos como lista de objetos Paso
        List<Paso> pasos = obtenerListaDePasos(jsonObject.getJSONArray("pasos"));

        // Crea y devuelve el objeto Receta
        return new Receta(idReceta, titulo, descripcion, tiempoCoccion, dificultad, ingredientes, pasos);
    }

    private List<String> obtenerListaDeCadenas(JSONArray jsonArray) throws JSONException {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            lista.add(jsonArray.getString(i));
        }
        return lista;
    }

    private void handleError(String errorMessage) {
        // Aquí puedes manejar el error, por ejemplo, mostrando un mensaje al usuario o realizando alguna otra acción
        Log.e("Error", errorMessage);
        // También puedes mostrar un mensaje de error en un Toast
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    // Método para modificar el título de la receta
    private void modificarTituloReceta(int idReceta, String nuevoTitulo) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/modificarTituloReceta";

        // Crea el cuerpo de la solicitud en formato JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("idReceta", idReceta);
            requestBody.put("titulo", nuevoTitulo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crea la solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                    }
                });

        // Añade la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }

    // Método para modificar la descripción, dificultad y tiempo de cocción de la receta
    private void modificarDescripcionDificultadTiempo(int idReceta, String nuevaDescripcion, String nuevoTiempo, String nuevaDificultad) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/modificarDescripcionReceta";

        // Crea el cuerpo de la solicitud en formato JSON
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("idReceta", idReceta);
            requestBody.put("descripcion", nuevaDescripcion);
            requestBody.put("tiempoCoccion", nuevoTiempo);
            requestBody.put("dificultad", nuevaDificultad);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crea la solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                    }
                });

        // Añade la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }

    // Método para modificar los ingredientes de la receta
    private void modificarIngredientes(int idReceta, List<Ingrediente> nuevosIngredientes) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/modificarIngredientes";

        // Crea el cuerpo de la solicitud en formato JSON
        JSONArray ingredientesArray = new JSONArray();
        for (Ingrediente ingrediente : nuevosIngredientes) {
            ingredientesArray.put(ingrediente.getNombre());
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("idReceta", idReceta);
            requestBody.put("ingredientes", ingredientesArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crea la solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                    }
                });

        // Añade la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }

    // Método para modificar los pasos de la receta
    private void modificarPasos(int idReceta, List<Paso> nuevosPasos) {
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/receta/modificarPasos";

        // Crea el cuerpo de la solicitud en formato JSON
        JSONArray pasosArray = new JSONArray();
        for (Paso paso : nuevosPasos) {
            JSONObject pasoObject = new JSONObject();
            try {
                pasoObject.put("titulo", paso.getTitulo());
                pasoObject.put("descripcion", paso.getDescripcion());
                pasosArray.put(pasoObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("idReceta", idReceta);
            requestBody.put("pasos", pasosArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crea la solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor si es necesario
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud si es necesario
                    }
                });

        // Añade la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(request);
    }
}

