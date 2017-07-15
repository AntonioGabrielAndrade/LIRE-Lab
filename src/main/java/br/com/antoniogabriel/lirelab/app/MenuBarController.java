package br.com.antoniogabriel.lirelab.app;

import javafx.event.ActionEvent;

import javax.inject.Inject;
import java.io.IOException;

public class MenuBarController {

    private AppController appController;

    @Inject
    public MenuBarController(AppController appController) {
        this.appController = appController;
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        appController.openCreateCollectionDialog(event);
    }

    public void openAboutDialog(ActionEvent event) {
        appController.openAboutDialog(event);
    }
}
