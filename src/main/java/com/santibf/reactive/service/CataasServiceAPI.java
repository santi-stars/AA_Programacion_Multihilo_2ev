package com.santibf.reactive.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santibf.reactive.model.Cat;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CataasServiceAPI {

    protected static final String BASE_URL = "https://cataas.com/";
    private CataasAPI cataasAPI;
    private Cat cat;

    // https://cataas.com/#/

    public CataasServiceAPI() {
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

        cataasAPI = retrofit.create(CataasAPI.class);
    }

    public Observable<Cat> getRandomCat() {

        Observable<Cat> originalObservable = this.cataasAPI.getRandomCat();
        // Transformador para modificar el atributo 'Url' y añadirle "BASE_URL"
        ObservableTransformer<Cat, Cat> transformer = observable ->
                observable.map(cat -> {
                    cat.setUrl(BASE_URL + cat.getUrl());
                    return cat;
                });
        // Aplicar la transformación y obtener el nuevo Observable<Cat>
        Observable<Cat> modifiedObservable = originalObservable.compose(transformer);

        return modifiedObservable;
    }

}
