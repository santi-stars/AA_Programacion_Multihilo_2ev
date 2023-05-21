package com.santibf.reactive.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santibf.reactive.model.CatFacts;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeowFactsServiceAPI {

    protected static final String BASE_URL = "https://meowfacts.herokuapp.com/";
    private MeowFactsAPI meowFactsAPI;

    // https://github.com/wh-iterabb-it/meowfacts

    public MeowFactsServiceAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient clientlogger = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientlogger)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        meowFactsAPI = retrofit.create(MeowFactsAPI.class);
    }

    /**
     * Carga un listado con el número de hechos de gatos que queramos
     * y en el idioma que elijamos
     * @param language abreviatura del idioma, ejem: "esp"
     * @param count número de "hechos de gatos"
     * @return devuelve un Observable
     */
    public Observable<CatFacts> getCatFacts(String language, int count) {
        return this.meowFactsAPI.getCatFacts(language, count);
    }

}
