package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML private StackPane createCollectionButtonPane;
    @FXML private StackPane searchCollectionButtonPane;

    private ApplicationCommands applicationCommands;
    private CommandComponentFactory commandComponentFactory;

    @Inject
    public WelcomeController(ApplicationCommands applicationCommands,
                             CommandComponentFactory commandComponentFactory) {

        this.applicationCommands = applicationCommands;
        this.commandComponentFactory = commandComponentFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupCreateCollectionButton();
        setupSearchCollectionButton();
    }

    private void setupCreateCollectionButton() {
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.CREATE);
        Button button = commandComponentFactory.createButton(createCommand, () -> null);
        createCollectionButtonPane.getChildren().add(button);
    }

    private void setupSearchCollectionButton() {
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.SEARCH);
        Button button = commandComponentFactory.createButton(createCommand, () -> null);
        button.setDisable(true);
        searchCollectionButtonPane.getChildren().add(button);
    }
}
