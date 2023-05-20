package com.santibf.reactive.service;

import com.santibf.reactive.model.Cat;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CataasAPI {
    // Obtiene JSon de gato
    @GET("cat?json=true")
    Observable<Cat> getRandomCat();
}
