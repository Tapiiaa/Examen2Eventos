package com.example.examen2eventos.app3;

public class Pharmacy {
    private String name;

    public String address;
    private double latitude;
    private double longitude;

    public Pharmacy() {} // Constructor vacío para Firebase

    public Pharmacy(String name, String adress, double latitude, double longitude) {
        this.name = name;
        this.address = adress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

