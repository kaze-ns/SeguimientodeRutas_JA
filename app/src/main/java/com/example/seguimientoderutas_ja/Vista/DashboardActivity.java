package com.example.seguimientoderutas_ja.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seguimientoderutas_ja.R;

public class DashboardActivity extends AppCompatActivity {

    Button Mapabtn;
    Button btnListas;

    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Mapabtn = findViewById(R.id.Mapabtn);
        btnListas = findViewById(R.id.btnListas);
        btnRegistro = findViewById(R.id.btnRegistro);


        Mapabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, MapaDeUbicacion.class));
            }
        });

        btnListas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, HistorialDeRutasActivity.class));
            }
        });
       btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, DetallesRutaActivity.class));
            }
        });
    }
}