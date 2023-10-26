package com.example.proyectoavocado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AgregaRecetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_receta);

        ImageButton btnHome = findViewById(R.id.btn_back);
        // Inicializa los EditText
        EditText editTextTitulo = findViewById(R.id.text_title);
        EditText editTextDescripcion = findViewById(R.id.text_descripcion);
        EditText editTextTiempoCoccion = findViewById(R.id.text_tiempoCoccion);
        EditText editTextDificultad = findViewById(R.id.text_tiempoCoccion);
        Button btn_ingredientes = findViewById(R.id.btn_ingredientes);
        LinearLayout contenedorIngredientes = findViewById(R.id.contenedor_ingredientes);
        Button btn_pasos = findViewById(R.id.btn_pasos);
        LinearLayout contenedor_pasos = findViewById(R.id.contenedor_pasos);
        Button btn_categorias = findViewById(R.id.btn_categorias);
        LinearLayout contenedor_categorias = findViewById(R.id.contenedor_categorias);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(AgregaRecetaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });



        
   }

}

