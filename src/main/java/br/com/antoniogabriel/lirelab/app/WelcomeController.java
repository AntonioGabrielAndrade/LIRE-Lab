package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static br.com.antoniogabriel.lirelab.app.ApplicationCommands.CollectionCommand.CREATE;
import static br.com.antoniogabriel.lirelab.app.ApplicationCommands.CollectionCommand.SEARCH;

public class WelcomeController implements Initializable {

    @FXML private StackPane createCollectionButtonPane;
    @FXML private StackPane searchCollectionButtonPane;

    private ApplicationCommands applicationCommands;
    private CommandTriggerFactory commandTriggerFactory;

    @Inject
    public WelcomeController(ApplicationCommands applicationCommands,
                             CommandTriggerFactory commandTriggerFactory) {

        this.applicationCommands = applicationCommands;
        this.commandTriggerFactory = commandTriggerFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupCreateCollectionButton();
        setupSearchCollectionButton();
    }

    private void setupCreateCollectionButton() {
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(CREATE);
        Button button = commandTriggerFactory.createButton(createCommand, () -> null);
        createCollectionButtonPane.getChildren().add(button);
    }

    private void setupSearchCollectionButton() {
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(SEARCH);
        Button button = commandTriggerFactory.createButton(createCommand, () -> null);
        button.setDisable(true);
        searchCollectionButtonPane.getChildren().add(button);
    }
}
