package com.example.seguimientoderutas_ja.Modelo;

public class Ubicacion {
    private String timestamp;
    private double latitude;
    private double longitude;

    // Constructor vacío requerido para Firebase
    public Ubicacion() {
    }

    public Ubicacion(String timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "timestamp='" + timestamp + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
