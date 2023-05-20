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
import java.util.ArrayList;
import java.util.List;

public class AppController {

    @FXML
    private TabPane tpCats;
    @FXML
    private Button btCat;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Slider sliderCats;
    @FXML
    private ChoiceBox<String> cbCatType;


//          https://cataas.com/cat/api/cats?tags=tag1,tag2&skip=0&limit=10
    @FXML
    public void initialize() {
        // Image image = new Image(R.getImage("cat_init.jpeg"));    // https://cataas.com/cat/ZtVyK9ZOtSn0pYWD
        Image image = new Image("https://cataas.com/cat/ZtVyK9ZOtSn0pYWD");    // https://cataas.com/cat/ZtVyK9ZOtSn0pYWD
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

    @FXML
    public void launchCatFacts(ActionEvent event) {

        System.out.println("Valor del CHOICE_BOX " +cbCatType.getValue());  // BORRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR
        System.out.println("Valor del SLIDER " + sliderCats.getValue());    // BORRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR
        loadCatFacts(cbCatType.getValue(), sliderCats.getValue());

    }

    private void loadCatFacts(String optionSelected, double numberOfFacts) {

        FXMLLoader loader = new FXMLLoader();   // Cargamos una pestaña nueva y su controlador
        loader.setLocation(R.getUI("cat_results.fxml"));
        CatsFactController catsFactController = new CatsFactController(optionSelected, (int) numberOfFacts);
        loader.setController(catsFactController);

        try {   // Lanzamos la pestaña
            VBox userBox = loader.load();
            tpCats.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            tpCats.getTabs().add(new Tab((int) numberOfFacts + " Datos de gatos", userBox));
            formatAlert();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Lanza una ventana de Alerta indicando el comentario del gato principal
     */
    private void formatAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso, mensaje de Moyete!!!");
        alert.setContentText("MIAAAUUU!!!");
        alert.show();

    }

}
