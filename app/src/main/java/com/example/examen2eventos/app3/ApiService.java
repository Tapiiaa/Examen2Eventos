package com.example.examen2eventos.app3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("https://examen2eventos-default-rtdb.firebaseio.com/Pharmacies.json")
    Call<List<Pharmacy>> getPharmacies();
}

