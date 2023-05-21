package com.santibf.reactive.service;

import com.santibf.reactive.model.Cat;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface CataasAPI {

    @GET("cats?limit=3000")     // Obtiene JSon de gatos con el tag seleccionado en el ChoiceBox
    Observable<List<Cat>> getRandomCatsByTag();

}
