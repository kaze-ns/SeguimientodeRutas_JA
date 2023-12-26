package com.example.seguimientoderutas_ja.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.seguimientoderutas_ja.R;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seguimientoderutas_ja.Modelo.Ubicacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeRutasActivity extends AppCompatActivity {

    private ListView listViewRutas;
    private DatabaseReference databaseReference;
    private List<Ubicacion> listaRutas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_de_rutas);

        // Inicializa Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("ubicaciones");

        listViewRutas = findViewById(R.id.listViewRutas);
        listaRutas = new ArrayList<>();

        // Obtiene las rutas desde Firebase y las muestra en el ListView
        obtenerRutasDesdeFirebase();
    }

    private void obtenerRutasDesdeFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaRutas.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ubicacion ubicacion = snapshot.getValue(Ubicacion.class);
                    if (ubicacion != null) {
                        listaRutas.add(ubicacion);
                    }
                }

                mostrarRutasEnListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error, si es necesario
            }
        });
    }

    private void mostrarRutasEnListView() {
        List<String> nombresRutas = new ArrayList<>();
        for (Ubicacion ubicacion : listaRutas) {
            nombresRutas.add("Timestamp: " + ubicacion.getTimestamp());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresRutas);
        listViewRutas.setAdapter(adapter);
    }
}
