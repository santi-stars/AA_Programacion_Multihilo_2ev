package com.santibf.reactive.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.santibf.reactive.util.R;

import java.io.IOException;

public class AppController {

    @FXML
    private Button btCat;
    @FXML
    private TabPane tpCats;
    @FXML
    public ImageView ivLogo;
    @FXML
    private Slider sliderCats;
    @FXML
    private ChoiceBox<String> cbCatType;

    //          https://cataas.com/cat/api/cats?tags=tag1,tag2&skip=0&limit=10
    @FXML
    public void initialize() {
        Image image = new Image("https://cataas.com/cat/ZtVyK9ZOtSn0pYWD");
        ivLogo.setImage(image);

        ObservableList<String> elementos = FXCollections.observableArrayList(
                "Sacando la lengua",     // blep
                "Gato dormido",            // sleep
                "Adorable gatito",             // cute
                "Mini michi"            // kitten
        );

        cbCatType.setItems(elementos);
        cbCatType.setValue(elementos.get(0));
    }

    /**
     * Lanza el método "loadCatFacts()" pasando de parámetros la opción seleccionada del ChoiceBox para elegir
     * la categoria de tipo de gato para la foto a mostrar y el valor del Slider para indicar el número de
     * datos curiosos de gatetes a mostrar
     *
     * @param event
     */
    @FXML
    public void launchCatFacts(ActionEvent event) {

        loadCatFacts(cbCatType.getValue(), sliderCats.getValue());

    }

    /**
     * Crea un FXMLLoader con su controlador y crea una nueva pestaña
     *
     * @param optionSelected Opción seleccionada del ChoiceBox para elegir
     *                       * la categoria de tipo de gato para la foto a mostrar
     * @param numberOfFacts  Valor del Slider para indicar el número de
     *                       * datos curiosos de gatetes a mostrar
     */
    private void loadCatFacts(String optionSelected, double numberOfFacts) {

        FXMLLoader loader = new FXMLLoader();   // Cargamos una pestaña nueva y su controlador
        CatsFactController catsFactController = new CatsFactController(optionSelected, (int) numberOfFacts);
        loader.setLocation(R.getUI("cat_results.fxml"));
        loader.setController(catsFactController);

        try {   // Lanzamos la pestaña
            VBox userBox = loader.load();
            tpCats.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            tpCats.getTabs().add(new Tab((int) numberOfFacts + " Datos de gatos", userBox));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
