package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ToolBarController implements Initializable {

    @FXML private CommandComboBox<Collection> searchCollectionComboBox;
    @FXML private HBox leftToolBar;
    @FXML private HBox rightToolBar;

    private ApplicationCommands applicationCommands;
    private CommandComponentFactory<Void> commandComponentFactory;
    private CollectionService collectionService;

    @Inject
    public ToolBarController(ApplicationCommands applicationCommands,
                             CommandComponentFactory commandComponentFactory,
                             CollectionService collectionService) {

        this.applicationCommands = applicationCommands;
        this.commandComponentFactory = commandComponentFactory;
        this.collectionService = collectionService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSearchComboBoxCollections();
        collectionService.addCollectionsChangeListener(() -> Platform.runLater(() -> setSearchComboBoxCollections()));
        initLeftToolBar();
        initRightTooBar();
    }

    public Collection getSelectedCollection() {
        return searchCollectionComboBox.getSelectedItem();
    }

    public void setSelectedCollection(Collection collection) {
        searchCollectionComboBox.setSelectedItem(collection);
    }

    private void setSearchComboBoxCollections() {
        searchCollectionComboBox.setItems(collectionService.getCollections());
        searchCollectionComboBox.setCommand(applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.SEARCH));
    }

    private void initLeftToolBar() {
        leftToolBar.getChildren().clear();
        List<Command<Void>> leftToolBarCommands = applicationCommands.getLeftToolBarCommands();
        for (Command<Void> command : leftToolBarCommands) {
            Button button = commandComponentFactory.createButton(command, () -> null);
            button.setId("toolbar-" + command.getNodeId());
            leftToolBar.getChildren().add(button);
        }

    }

    private void initRightTooBar() {
        rightToolBar.getChildren().clear();
        List<Command<Void>> commands = applicationCommands.getRightToolBarCommands();
        for (Command<Void> command : commands) {
            Button button = commandComponentFactory.createButton(command, () -> null);
            button.setId("toolbar-" + command.getNodeId());
            rightToolBar.getChildren().add(button);
        }
        rightToolBar.getChildren().add(searchCollectionComboBox);
    }
}
