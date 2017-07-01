package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.inject.Inject;

public class App extends Application {

    @Inject private AppFXML appFXML;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        DependencyInjection.init(this);
        stage.setMaximized(true);
        appFXML.loadIn(stage);
    }
}