package com.example.seguimientoderutas_ja.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seguimientoderutas_ja.Modelo.Ruta;
import com.example.seguimientoderutas_ja.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetallesRutaActivity extends AppCompatActivity {

    private TextView textViewDistancia, textViewDuracion, textViewResumen;
    private EditText editTextNuevaDistancia, editTextNuevaDuracion;
    private DatabaseReference databaseReference;
    private String rutaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ruta);

        // Inicializar Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("rutas");

        // Obtener referencias a las vistas
        textViewDistancia = findViewById(R.id.textViewDistancia);
        textViewDuracion = findViewById(R.id.textViewDuracion);
        textViewResumen = findViewById(R.id.textViewResumen);
        editTextNuevaDistancia = findViewById(R.id.editTextNuevaDistancia);
        editTextNuevaDuracion = findViewById(R.id.editTextNuevaDuracion);

        // Recuperar el ID de la ruta desde el Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rutaId = extras.getString("rutaId");

            // Recuperar los detalles de la ruta desde Firebase
            databaseReference.child(rutaId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Ruta ruta = dataSnapshot.getValue(Ruta.class);

                    if (ruta != null) {
                        // Actualizar las vistas con los detalles de la ruta
                        textViewDistancia.setText("Distancia: " + ruta.getDistancia());
                        textViewDuracion.setText("Duración: " + ruta.getDuracion());
                        textViewResumen.setText("Resumen: " + ruta.getResumen());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores si es necesario
                }
            });
        }
    }

    // Método para manejar el clic en el botón Modificar
    public void onModificarClick(View view) {
        // Obtener nuevos valores de los EditText
        String nuevaDistancia = editTextNuevaDistancia.getText().toString().trim();
        String nuevaDuracion = editTextNuevaDuracion.getText().toString().trim();

        // Verificar que los nuevos valores no estén vacíos
        if (!nuevaDistancia.isEmpty() && !nuevaDuracion.isEmpty()) {
            // Actualizar los valores en Firebase
            databaseReference.child(rutaId).child("distancia").setValue(nuevaDistancia);
            databaseReference.child(rutaId).child("duracion").setValue(nuevaDuracion);

            // Actualizar las vistas con los nuevos valores en el hilo principal
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewDistancia.setText("Distancia: " + nuevaDistancia);
                    textViewDuracion.setText("Duración: " + nuevaDuracion);
                }
            });

            // Limpiar los EditText
            editTextNuevaDistancia.setText("");
            editTextNuevaDuracion.setText("");
        }
    }

    // Método para manejar el clic en el botón Agregar
    public void onAgregarClick(View view) {
        // Obtener nuevos valores de los EditText
        String nuevaDistancia = editTextNuevaDistancia.getText().toString().trim();
        String nuevaDuracion = editTextNuevaDuracion.getText().toString().trim();

        // Verificar que los nuevos valores no estén vacíos
        if (!nuevaDistancia.isEmpty() && !nuevaDuracion.isEmpty()) {
            // Crear un nuevo objeto Ruta con los nuevos valores
            Ruta nuevaRuta = new Ruta(nuevaDistancia, nuevaDuracion, "Resumen temporal");

            // Agregar la nueva ruta a Firebase
            DatabaseReference nuevaRutaRef = databaseReference.push();
            nuevaRutaRef.setValue(nuevaRuta);

            // Limpiar los EditText
            editTextNuevaDistancia.setText("");
            editTextNuevaDuracion.setText("");

            // Recuperar el ID de la nueva ruta
            String nuevaRutaId = nuevaRutaRef.getKey();

            // Recuperar los detalles de la nueva ruta desde Firebase
            databaseReference.child(nuevaRutaId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Ruta ruta = dataSnapshot.getValue(Ruta.class);

                    if (ruta != null) {
                        // Actualizar las vistas con los detalles de la nueva ruta
                        textViewDistancia.setText("Distancia: " + ruta.getDistancia());
                        textViewDuracion.setText("Duración: " + ruta.getDuracion());
                        textViewResumen.setText("Resumen: " + ruta.getResumen());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores si es necesario
                }
            });
        }
    }
}


// Método para manejar el clic en el botón Agregar
