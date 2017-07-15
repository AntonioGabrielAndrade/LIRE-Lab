package br.com.antoniogabriel.lirelab.app;

import javafx.event.ActionEvent;

import javax.inject.Inject;
import java.io.IOException;

public class WelcomeController {


    private AppController appController;

    @Inject
    public WelcomeController(AppController appController) {
        this.appController = appController;
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        appController.openCreateCollectionDialog(event);
    }
}
