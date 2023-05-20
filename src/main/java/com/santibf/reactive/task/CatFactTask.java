package com.santibf.reactive.task;

import com.santibf.reactive.model.CatFacts;
import com.santibf.reactive.service.MeowFactsServiceAPI;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.commons.lang3.time.StopWatch;

public class CatFactTask extends Task<Integer> {

    private ObservableList<String> results;
    private int counter;
    private int count;
    private String language;

    public CatFactTask(ObservableList<String> results, int count, String language) {
        this.results = results;
        this.counter = 0;
        this.count = count;
        this.language = language;
    }

    @Override
    protected Integer call() throws Exception {

        MeowFactsServiceAPI meowFactsServiceAPI = new MeowFactsServiceAPI();
        StopWatch watch = new StopWatch();  // Instancia un reloj
        watch.start();                      // Inicia el reloj

        Consumer<CatFacts> catFactConsumer = (catFacts) -> {

            for (String elemento : catFacts.getData()) {
                this.counter++;
                elemento = "[ " + counter + " ] - " + elemento;
                Thread.sleep(250);
                updateMessage(String.valueOf(this.counter) + " de " + count + " Descargados en: " + watch.getTime() / 1000 + " sg");
                String finalElemento = insertLineBreak(elemento, 99);
                Platform.runLater(() -> this.results.add(finalElemento));
            }

        };

        meowFactsServiceAPI.getCatFacts("esp", this.count).subscribe(catFactConsumer);

        return null;
    }

    /**
     * Inserta un salto de línea cada X caracteres para adecuar el texto a la interfaz
     *
     * @param texto              Cada dato curioso cargado de la API
     * @param caracteresPorLinea Caracteres por cada línea antes del salto de línea
     * @return Devuelve el texto formateado
     */
    public static String insertLineBreak(String texto, int caracteresPorLinea) {
        StringBuilder sb = new StringBuilder(texto);
        int contador = 0;
        int ultimaPosicionEspacio = -1;

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ') {
                ultimaPosicionEspacio = i;
            }

            contador++;

            if (contador == caracteresPorLinea) {
                if (ultimaPosicionEspacio != -1) {
                    sb.setCharAt(ultimaPosicionEspacio, '\n');
                    contador = i - ultimaPosicionEspacio;
                    ultimaPosicionEspacio = -1;
                } else {
                    sb.insert(i, '\n');
                    contador = 0;
                }
            }
        }

        return sb.toString();
    }
}

