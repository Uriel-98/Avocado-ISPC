package com.example.proyectoavocado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //capturo los id de los botones
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnBuscarReceta = findViewById(R.id.btn_buscar);
        ImageButton btnAgregarReceta = findViewById(R.id.btn_agregar);
        ImageButton btnFavoritos = findViewById(R.id.btn_favoritos);
        ImageButton btnPerfil = findViewById(R.id.btn_perfil);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FeedActivity
                Intent intent = new Intent(PerfilActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir AgregarRecetaActivity
                Intent intent = new Intent(PerfilActivity.this, AgregaRecetaActivity.class);
                startActivity(intent);
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir FavoritosActivity
                Intent intent = new Intent(PerfilActivity.this, favoritesActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir PerfilActivity
                Intent intent = new Intent(PerfilActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }
}