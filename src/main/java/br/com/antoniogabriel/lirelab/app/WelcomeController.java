package br.com.antoniogabriel.lirelab.app;

import javafx.event.ActionEvent;

import javax.inject.Inject;
import java.io.IOException;

public class WelcomeController {

    private ToolBarController toolBarController;

    @Inject
    public WelcomeController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        toolBarController.openCreateCollectionDialog(event);
    }
}
