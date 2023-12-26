package com.example.seguimientoderutas_ja.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.seguimientoderutas_ja.Modelo.Ubicacion;
import com.example.seguimientoderutas_ja.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MapaDeUbicacion extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private EditText txtLatitud, txtLongitud;

    private DatabaseReference databaseReference;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean recording = false;
    private Button btnStartRecording, btnStopRecording;

    private Button menuButton;

    // Variable para almacenar el marcador de tu ubicación
    private Marker miUbicacionMarker;

    // Lista para almacenar todos los marcadores adicionales
    private List<Marker> marcadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_de_ubicacion);

        // Inicializa Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ubicaciones");

        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);
        btnStartRecording = findViewById(R.id.btnStartRecording);
        btnStopRecording = findViewById(R.id.btnStopRecording);

        menuButton = findViewById(R.id.menubtn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Verificar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        btnStartRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording(); // Llama al método para iniciar la grabación
            }
        });

        btnStopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

    }
    private void startRecording() {
        recording = true;
        btnStartRecording.setVisibility(View.GONE);
        btnStopRecording.setVisibility(View.VISIBLE);
        // Puedes agregar lógica adicional para iniciar la grabación aquí
        Toast.makeText(this, "Grabación iniciada", Toast.LENGTH_SHORT).show();

        // Obtiene la ubicación actual
        obtenerUbicacionActual();
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_mapa_de_ubicacion, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        popupMenu.show();
    }


    private void obtenerUbicacionActual() {
        // Aquí debes agregar tu lógica para obtener la ubicación actual
        // Por ejemplo, utilizando la API de ubicación de Android
        // Asumiendo que obtienes las coordenadas en las variables latitud y longitud

        double latitud = obtenerLatitudActual();  // Reemplaza esto con tu lógica real
        double longitud = obtenerLongitudActual();  // Reemplaza esto con tu lógica real

        // Obtiene la marca de tiempo actual
        String timestamp = obtenerMarcaDeTiempoActual();

        // Guarda la ubicación en Firebase
        guardarUbicacionEnFirebase(timestamp, latitud, longitud);
    }
    private void guardarUbicacionEnFirebase(String timestamp, double latitud, double longitud) {
        // Crea un nuevo nodo en la base de datos con la marca de tiempo como clave
        DatabaseReference ubicacionRef = databaseReference.child(timestamp);

        // Crea un objeto Ubicacion
        Ubicacion ubicacion = new Ubicacion(timestamp, latitud, longitud);

        // Guarda la ubicación en Firebase
        ubicacionRef.setValue(ubicacion);

        Toast.makeText(this, "Ubicación guardada en Firebase", Toast.LENGTH_SHORT).show();
    }

    private double obtenerLatitudActual() {
        // Implementa la lógica para obtener la latitud actual
        return 0.0;
    }

    private double obtenerLongitudActual() {
        // Implementa la lógica para obtener la longitud actual
        return 0.0;
    }

    private String obtenerMarcaDeTiempoActual() {
        // Implementa la lógica para obtener la marca de tiempo actual
        // Puedes usar SimpleDateFormat o cualquier otra lógica que prefieras
        return "marca_de_tiempo_actual";
    }
    private void stopRecording() {
        recording = false;
        btnStartRecording.setVisibility(View.VISIBLE);
        btnStopRecording.setVisibility(View.GONE);
        // Puedes agregar lógica adicional para detener la grabación aquí
        Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show();

        // Detener las actualizaciones de ubicación
        detenerActualizacionesDeUbicacion();

        // Puedes agregar lógica adicional para detener la grabación en Firebase aquí
        // En este caso, dejar de escuchar las actualizaciones de ubicación en Firebase
        // y realizar cualquier otra acción necesaria para detener la grabación en la base de datos
    }

    private void detenerActualizacionesDeUbicacion() {
        // Detener las actualizaciones de ubicación cuando la actividad se detiene
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000); // Actualizar cada 5 segundos

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    LatLng currentLocation = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

                    // Actualiza la posición del marcador de tu ubicación
                    actualizarUbicacionMarker(currentLocation);

                    // Graba la ubicación en Firebase
                    guardarUbicacionFirebase(currentLocation);

                    if (recording) {
                        // Aquí puedes agregar lógica adicional para manejar la ubicación durante la grabación
                    }
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void actualizarUbicacionMarker(LatLng currentLocation) {
        if (miUbicacionMarker == null) {
            // Si no hay un marcador para tu ubicación, agrégalo
            miUbicacionMarker = mMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Mi Ubicación"));
        } else {
            // Si ya hay un marcador para tu ubicación, actualiza su posición
            miUbicacionMarker.setPosition(currentLocation);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

        txtLatitud.setText(String.valueOf(currentLocation.latitude));
        txtLongitud.setText(String.valueOf(currentLocation.longitude));
    }

    private void guardarUbicacionFirebase(LatLng location) {
        // Obtiene la hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        // Crea un nuevo nodo en la base de datos para almacenar la ubicación
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(new Ubicacion(currentDateAndTime, location.latitude, location.longitude));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            // Manejar el caso en el que el usuario no otorga permisos
            Toast.makeText(this, "Se requieren permisos de ubicación para la aplicación.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Tu lógica al hacer clic en el mapa
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        // Llamado cuando se realiza un clic largo en el mapa
        if (miUbicacionMarker == null) {
            // Si aún no hay un marcador para tu ubicación, agrégalo
            miUbicacionMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Mi Ubicación"));
        } else {
            // Si ya hay un marcador para tu ubicación, actualiza su posición
            miUbicacionMarker.setPosition(latLng);
        }

        // Añade un marcador en la posición proporcionada
        añadirMarcador(latLng, "Nuevo Marcador");
    }

    // Método para agregar un marcador en la posición proporcionada
    // Método para agregar un marcador en la posición proporcionada
    private void añadirMarcador(LatLng position, String title) {
        if (mMap != null) {
            // Añade un marcador en la posición proporcionada
            Marker nuevoMarcador = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(title));

            // Muestra la latitud y longitud en los campos de texto
            txtLatitud.setText(String.valueOf(position.latitude));
            txtLongitud.setText(String.valueOf(position.longitude));

            // Añade el nuevo marcador a la lista
            marcadores.add(nuevoMarcador);

            // Asigna un clic al marcador recién creado
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // Muestra un mensaje al presionar el marcador
                    Toast.makeText(MapaDeUbicacion.this, "Marcador presionado: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                    // Muestra la latitud y longitud en los campos de texto
                    txtLatitud.setText(String.valueOf(position.latitude));
                    txtLongitud.setText(String.valueOf(position.longitude));

                    // Puedes iniciar aquí el proceso para realizar la ruta
                    // Por ejemplo, puedes abrir una nueva actividad para la navegación

                    return false; // Devuelve false para que se ejecute el comportamiento predeterminado (info window)
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener las actualizaciones de ubicación cuando la actividad se destruye
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa_de_ubicacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_map_type_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.menu_map_type_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.menu_map_type_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.menu_map_type_hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            // Agrega casos para los nuevos tipos de mapas según sea necesario
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Método para realizar una acción cuando se presiona un botón (puedes llamarlo desde algún botón de tu interfaz)
    public void realizarAccion(View view) {
        // Iterar sobre la lista de marcadores y realizar alguna acción
        for (Marker marcador : marcadores) {
            // Ejemplo: Mostrar el título de cada marcador
            Log.d("Marcador", "Título del marcador: " + marcador.getTitle());
        }
    }

    // Método para obtener la posición actual del usuario desde el mapa
    private LatLng obtenerPosicionActual() {
        // Aquí debes implementar la lógica para obtener la posición actual del usuario
        // Puedes usar la ubicación actual si está disponible
        // En este ejemplo, se asume una ubicación ficticia (latitud 0, longitud 0)
        return new LatLng(0, 0);
    }
}
