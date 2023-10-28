package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarPerfilActivity extends AppCompatActivity {

    EditText perfilPassword1;
    EditText perfilPassword2;
    TextView perfilEmail;
    EditText perfilNombreCompleto;

    EditText perfilNombreUsuario;

    String nombrePlaceholder;

    String usuarioPlaceholder;
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

        ImageButton btnEditNombre = findViewById(R.id.btnEditNombre);
        ImageButton btnAceptarEditNombre = findViewById(R.id.btnAceptarEditNombre);
        ImageButton btnCancelEditNombre = findViewById(R.id.btnCancelEditNombre);
        ImageButton btnCambiarContraseña = findViewById(R.id.btnCambiarContraseña);
        ImageButton btnCancelCambiarContraseña = findViewById(R.id.btnCancelCambiarContraseña);
        ImageButton btnAceptarCambiarContraseña = findViewById(R.id.btnAceptarCambiarContraseña);


        //EditTexts
        perfilPassword1 = findViewById(R.id.perfilPassword1);
        perfilEmail = findViewById(R.id.perfilEmail);
        perfilNombreCompleto = findViewById(R.id.perfilNombreCompleto);
        perfilNombreUsuario = findViewById(R.id.perfilNombreUsuario);

        perfilPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Deshabilitar los EditText
        perfilNombreCompleto.setEnabled(false);
        perfilNombreUsuario.setEnabled(false);
        perfilPassword1.setEnabled(false);

        traerDatosPerfil("lalari@example.com");

        btnCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCambiarContraseña.setVisibility(View.GONE);
                btnAceptarCambiarContraseña.setVisibility(View.VISIBLE);
                btnCancelCambiarContraseña
            }
        });

        btnEditNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditNombre.setVisibility(View.GONE);
                btnAceptarEditNombre.setVisibility(View.VISIBLE);
                btnCancelEditNombre.setVisibility(View.VISIBLE);

                usuarioPlaceholder = perfilNombreUsuario.getText().toString();
                nombrePlaceholder = perfilNombreCompleto.getText().toString();

                perfilNombreCompleto.setEnabled(true);
                perfilNombreUsuario.setEnabled(true);
            }
        });

        btnCancelEditNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilNombreUsuario.setText(usuarioPlaceholder);
                perfilNombreCompleto.setText(nombrePlaceholder);

                perfilNombreCompleto.setEnabled(false);
                perfilNombreUsuario.setEnabled(false);

                btnAceptarEditNombre.setVisibility(View.GONE);
                btnCancelEditNombre.setVisibility(View.GONE);
                btnEditNombre.setVisibility(View.VISIBLE);
            }
        });

        btnAceptarEditNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //llamar actualizar
                actualizarNombres(String.valueOf(perfilEmail));
              // cambiar visibilidad
                btnAceptarEditNombre.setVisibility(View.GONE);
                btnCancelEditNombre.setVisibility(View.GONE);
                btnEditNombre.setVisibility(View.VISIBLE);
              // deshabilitar
                perfilNombreUsuario.setEnabled(false);
                perfilNombreCompleto.setEnabled(false);
            }
        });

        // Otros botones
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

    private void traerDatosPerfil(String userEmail){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/usuario/getUsuario/" + userEmail;


        StringRequest get = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String jsonString = json.toString();
                    JSONObject content = json.getJSONObject("content");
                    Log.d("JSON",jsonString);
                    String nombreCompleto = content.getString("nombreCompleto");
                    String usuario =  content.getString("usuario");
                    String email =  content.getString("email");
                    // agregar imagen después

                    perfilNombreCompleto.setText(nombreCompleto);
                    perfilNombreUsuario.setText(usuario);
                    perfilEmail.setText(email);



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
        });
        Volley.newRequestQueue(this).add(get);
    }

    private void actualizarNombres(String userEmail){
        String pc_ip = getResources().getString(R.string.pc_ip);
        String url = "http://" + pc_ip + ":3000/usuario/actualizarPerfil?_method=PUT";

        JSONObject datos = new JSONObject();
        try {
            datos.put("nombreCompleto", perfilNombreCompleto.getText().toString());
            datos.put("usuario", perfilNombreUsuario.getText().toString());
            datos.put("email", perfilEmail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest post = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String message = json.getString("message");
                    Toast.makeText(getApplicationContext(),  message, Toast.LENGTH_LONG).show();
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
        }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return datos.toString().getBytes();
            }
        };

        Volley.newRequestQueue(this).add(post);
    }


}