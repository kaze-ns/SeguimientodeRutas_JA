package com.example.seguimientoderutas_ja.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.seguimientoderutas_ja.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seguimientoderutas_ja.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seguimientoderutas_ja.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class activity_Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, btnIngresarRegistro, btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();

        btnIngresarRegistro = findViewById(R.id.btnregresarRegistro);

        btnIngresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí agregas la lógica para ir al activity_register
                irAActivityRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }

    private void irAActivityRegister() {
        Intent intent = new Intent(this, activity_Register.class);
        startActivity(intent);
    }

    private void iniciarSesion() {
        // Obtener los valores ingresados por el usuario
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity_Login.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión utilizando Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity_Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            Toast.makeText(activity_Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Redirigir al usuario a la pantalla de contactos (activity_contacts)
                            Intent intent = new Intent(activity_Login.this, DashboardActivity.class);
                            startActivity(intent);
                            finish(); // Cerrar la actividad actual para que el usuario no pueda volver atrás

                        } else {
                            // Inicio de sesión fallido
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(activity_Login.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void cerrarSesion() {
        // Cerrar sesión en Firebase
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            Toast.makeText(activity_Login.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity_Login.this, "No hay sesión activa", Toast.LENGTH_SHORT).show();
        }
    }
}
