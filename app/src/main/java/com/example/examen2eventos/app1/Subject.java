package com.example.examen2eventos.app1;

public class Subject {
    private String id;
    private String name;
    private String days;
    private String hours;

    public Subject() {
        // Constructor vacío requerido por Firebase
    }

    public Subject(String id, String name, String days, String hours) {
        this.id = id;
        this.name = name;
        this.days = days;
        this.hours = hours;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}

