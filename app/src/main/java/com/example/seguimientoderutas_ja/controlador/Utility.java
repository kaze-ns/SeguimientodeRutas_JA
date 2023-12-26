package com.example.seguimientoderutas_ja.controlador;

public class Utility {

    public static final String TABLA_UBICACION = "ubicacion";
    public static final String CAMPO_ID = "id"; // Nuevo campo ID
    public static final String CAMPO_TIMESTAMP = "timestamp";
    public static final String CAMPO_LATITUD = "latitud";
    public static final String CAMPO_LONGITUD = "longitud";
    public static final String CREAR_TABLA_UBICACION = "CREATE TABLE " +
            TABLA_UBICACION + " (" +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CAMPO_TIMESTAMP + " TEXT, " +
            CAMPO_LATITUD + " REAL, " +
            CAMPO_LONGITUD + " REAL);";

    public static final String TABLA_RUTA = "ruta";
    public static final String RUTA_ID = "id"; // Nuevo campo ID
    public static final String CAMPO_DISTANCIA = "distancia";
    public static final String CAMPO_DURACION = "duracion";
    public static final String CAMPO_RESUMEN = "resumen";
    public static final String CREAR_TABLA_RUTA = "CREATE TABLE " +
            TABLA_RUTA + " (" +
            RUTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CAMPO_DISTANCIA + " TEXT, " +
            CAMPO_DURACION + " TEXT, " +
            CAMPO_RESUMEN + " TEXT);";

}
