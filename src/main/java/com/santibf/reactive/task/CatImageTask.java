package com.santibf.reactive.task;

import com.santibf.reactive.model.Cat;
import com.santibf.reactive.service.CataasServiceAPI;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class CatImageTask extends Task<Integer> {

    @FXML
    private ImageView ivLogo;

    public CatImageTask() {
    }

    @Override
    protected Integer call() throws Exception {
/*
        CataasServiceAPI cataasServiceAPI = new CataasServiceAPI();

        Consumer<Cat> catFactConsumer = (cat) -> {
            Platform.runLater(() -> this.results.add(cat.toString()));
        };
        meowFactsServiceAPI.getCatFacts("esp", this.count).subscribe(catFactConsumer);
*/

        return null;
    }

}

