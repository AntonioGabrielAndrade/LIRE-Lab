package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Inject;

public class App extends Application {

    @Inject private AppFXML appFXML;

    @Override
    public void init() throws Exception {
        DependencyInjection.init(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(true);
        appFXML.loadIn(stage);
    }
}
