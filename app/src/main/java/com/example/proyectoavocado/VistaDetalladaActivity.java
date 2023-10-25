package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

public class VistaDetalladaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_detallada);

        //capturo los id de los botones
        ImageButton btnBack = findViewById(R.id.btn_backFeed);
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);
        ImageButton btnMenuReceta = findViewById(R.id.btn_menu_receta);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(VistaDetalladaActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }
}