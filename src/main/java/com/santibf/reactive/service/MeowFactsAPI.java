package com.santibf.reactive.service;

import com.santibf.reactive.model.CatFacts;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeowFactsAPI {

    @GET("/")   // Obtiene JSon de datos curiosos de gatos donde "lang" es el idioma y "count" el nº de datos
    Observable<CatFacts> getCatFacts(@Query("lang") String language, @Query("count") int count);
}
