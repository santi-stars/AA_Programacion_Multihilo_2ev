package com.santibf.reactive.task;

import com.santibf.reactive.model.Cat;
import com.santibf.reactive.service.CataasServiceAPI;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CatUrlImageTask extends Task<Integer> {

    private String optionSelected;

    public CatUrlImageTask(String optionSelected) {
        this.optionSelected = optionSelected;
    }

    @Override
    protected Integer call() throws Exception {

        CataasServiceAPI cataasServiceAPI = new CataasServiceAPI();

        Consumer<Cat> finalCat = (info) -> Platform.runLater(() -> {

            try {
                URL url = new URL(cataasServiceAPI.BASE_URL_IMAGE + info.get_id());
                loadCatImage(String.valueOf(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        });

        switch (optionSelected) {   // Según opción seleccionada manda un tag para que el stream filtre
            case "Sacando la lengua":
                cataasServiceAPI.getUrlImageCatByTag("blep").subscribe(finalCat);
                break;
            case "Gato dormido":
                cataasServiceAPI.getUrlImageCatByTag("sleep").subscribe(finalCat);
                break;
            case "Adorable gatito":
                cataasServiceAPI.getUrlImageCatByTag("cute").subscribe(finalCat);
                break;
            case "Mini michi":
                cataasServiceAPI.getUrlImageCatByTag("kitten").subscribe(finalCat);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
        return null;

    }

    /**
     * Crea una ventana para mostrar la foto del gato
     *
     * @param imageUrl Url de descarga de la imagen seleccionada
     */
    @FXML
    public void loadCatImage(String imageUrl) {
        Stage stage = new Stage();
        ImageView imageView = new ImageView();

        imageView.setFitWidth(400); // Ancho del ImageView
        imageView.setPreserveRatio(true); // Mantener la proporción de la imagen

        Image image = loadImageFromUrl(imageUrl);   // Cargar la imagen desde la URL y asignarla al ImageView

        if (image != null) {
            imageView.setImage(image);
        } else {
            System.out.println("No se pudo cargar la imagen en el ImageView");
        }

        StackPane root = new StackPane(imageView);          // Crear el contenedor de la ventana y agregar el ImageView
        Scene scene = new Scene(root, 600, 400);    // Tamaño deseado de la ventana

        stage.setTitle("MIIIAAAUUU!!!");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Carga la imagen desde la URL suministrada
     *
     * @param imageUrl URL de la imagen a descargar
     * @return Devuelve una imagen de tipo Image de JavaFx
     */
    private Image loadImageFromUrl(String imageUrl) {

        try {

            URL url = new URL(imageUrl);            // Abre conexión a URL y obtiene flujo de entrada de la imagen
            InputStream inputStream = url.openStream();

            Image image = new Image(inputStream);   // Crear un objeto Image a partir del flujo de entrada

            inputStream.close();                    // Cerrar el flujo de entrada

            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
