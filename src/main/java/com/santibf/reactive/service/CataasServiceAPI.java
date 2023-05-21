package com.santibf.reactive.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.santibf.reactive.model.Cat;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.NoSuchElementException;

public class CataasServiceAPI {

    public final String BASE_URL = "https://cataas.com/api/";
    public final String BASE_URL_IMAGE = "https://cataas.com/cat/";
    private CataasAPI cataasAPI;

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

    /**
     * Crea un objeto de "Cat" Observable simple a traves de una consulta a la API que obtiene 3000 resultados,
     * lo filtra por el tag (Tema seleccionado en el ChoiceBox para la foto del gatete), lo pasa todo a una lista
     * y si no está vacía devuelve un elemento aleatoriamente
     *
     * @param tag Tema seleccionado en el ChoiceBox para la foto del gatete
     * @return Devuelve un objeto de gato
     */
    public Single<Cat> getUrlImageCatByTag(String tag) {

        Single<Cat> filteredCatObservable = this.cataasAPI.getRandomCatsByTag()
                .observeOn(Schedulers.computation())
                .flatMapIterable(catList -> catList)
                .filter(cat -> cat.getTags().contains(tag))
                .toList()
                .map(catList -> {
                    if (!catList.isEmpty()) {
                        int randomIndex = (int) (Math.random() * catList.size());
                        return catList.get(randomIndex);
                    } else {
                        throw new NoSuchElementException("No se encontraron gatos con la etiqueta especificada");
                    }
                })
                .observeOn(Schedulers.io());

        return filteredCatObservable;
    }

}
