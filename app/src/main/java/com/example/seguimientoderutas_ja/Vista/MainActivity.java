package com.example.seguimientoderutas_ja.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.seguimientoderutas_ja.R;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.seguimientoderutas_ja.R;

public class MainActivity extends AppCompatActivity {
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_Login.class));
            }
        });

        Toast.makeText(this, "Ejecutando la Aplicacion", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Aplicacion de Rutas Ejecutada", Toast.LENGTH_SHORT).show();
    }

}
