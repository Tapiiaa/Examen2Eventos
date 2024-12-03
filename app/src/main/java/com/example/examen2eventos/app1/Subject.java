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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDays() {
        return days;
    }

    public String getHours() {
        return hours;
    }
}
