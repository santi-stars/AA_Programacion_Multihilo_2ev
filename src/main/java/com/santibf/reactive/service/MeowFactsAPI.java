package com.santibf.reactive.service;

import com.santibf.reactive.model.CatFacts;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeowFactsAPI {


    @GET("/")   // https://meowfacts.herokuapp.com/?lang=esp&count=60
    Observable<CatFacts> getCatFacts(@Query("lang") String language, @Query("count") int count);
}
