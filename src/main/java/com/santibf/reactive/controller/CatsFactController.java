package com.santibf.reactive.controller;

import com.opencsv.CSVWriter;
import com.santibf.reactive.task.CatFactTask;
import com.santibf.reactive.util.ZipFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CatsFactController {

    @FXML
    private ListView<String> resultsListView;
    @FXML
    private Button btExport;
    @FXML
    private Label lbStatus;
    @FXML
    private TextField deleteInput;

    private static final String LANGUAGE = "esp";    // Idioma español por defecto

    private ObservableList<String> results;
    private CatFactTask catFactTask;
    private Executor executor;
    private String optionSelected;
    private int contador;
    private int intFacts;

    public CatsFactController(String optionSelected, int intFacts) {
        this.results = FXCollections.observableArrayList();
        this.executor = Executors.newFixedThreadPool(3);
        this.optionSelected = optionSelected;
        this.intFacts = intFacts;
        this.contador = 0;
    }

    @FXML
    public void initialize() {
        this.results.clear();
        this.resultsListView.setItems(this.results);
        this.catFactTask = new CatFactTask(this.results, this.intFacts, LANGUAGE);
        this.catFactTask.messageProperty().addListener((observableValue, oldValue, newValue) -> this.lbStatus.setText(newValue));
        new Thread(catFactTask).start();
    }

    @FXML
    public void deleteEntry(ActionEvent event) {
        this.results.remove(Integer.parseInt(deleteInput.getText()) - 1);
    }

    @FXML
    public void exportCSV(ActionEvent event) {
        String outputFileName = System.getProperty("user.dir") + System.getProperty("file.separator")
                + contador + "_cats_facts_list.csv";
        contador++;

        File outputFile = new File(outputFileName);

        CompletableFuture<Void> myFuture = CompletableFuture.runAsync(() -> {

            try {
                FileWriter writer = new FileWriter(outputFile);
                CSVWriter csvWriter = new CSVWriter(writer);
                List<String[]> data = new ArrayList<>();
                for (String catFact : this.results) {
                    data.add(new String[]{catFact});
                }
                csvWriter.writeAll(data);
                csvWriter.close();
                ZipFile.createZipFile(outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, executor);

    }   // END exportCSV

}
