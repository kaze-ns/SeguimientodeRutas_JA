package com.example.seguimientoderutas_ja.Modelo;

public class Ruta {
    private String distancia;
    private String duracion;
    private String resumen;

    public Ruta() {
        // Constructor vacío necesario para Firebase
    }

    public Ruta(String distancia, String duracion, String resumen) {
        this.distancia = distancia;
        this.duracion = duracion;
        this.resumen = resumen;
    }

    public String getDistancia() {
        return distancia;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getResumen() {
        return resumen;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "distancia='" + distancia + '\'' +
                ", duracion='" + duracion + '\'' +
                ", resumen='" + resumen + '\'' +
                '}';
    }
}

