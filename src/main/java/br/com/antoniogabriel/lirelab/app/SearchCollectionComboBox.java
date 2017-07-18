package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Command;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class SearchCollectionComboBox extends HBox {

    private static final String SEARCH_COMBOBOX_FXML = "search-combobox.fxml";

    private ButtonCommandFactory factory = new ButtonCommandFactory();

    @FXML private ComboBox<Collection> collectionsComboBox;
    @FXML private Button searchButton;

    private SimpleObjectProperty<Command<Collection>> commandProperty = new SimpleObjectProperty<>();

    public SearchCollectionComboBox() {
        loadFXML();
    }

    private Node updateButton() {
        return getChildren().set(1, factory.createButton(commandProperty.getValue(), () -> collectionsComboBox.getValue()));
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(SEARCH_COMBOBOX_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setCollections(List<Collection> collections) {
        collectionsComboBox.getItems().clear();
        collectionsComboBox.setItems(FXCollections.observableList(collections));
        collectionsComboBox.getSelectionModel().select(0);
    }

    public void setSelectedCollection(Collection collection) {
        collectionsComboBox.getSelectionModel().select(collection);
    }

    public Collection getSelectedCollection() {
        return collectionsComboBox.getValue();
    }

    public Command<Collection> getCommand() {
        return commandProperty.get();
    }

    public void setCommand(Command<Collection> command) {
        this.commandProperty.set(command);
        updateButton();
    }

    class ButtonCommandFactory {

        public Button createButton(Command<Collection> command, CommandProvider<Collection> provider) {
            Button button = new Button();
            button.setId("toolbar-search-collection");

            button.setGraphic(command.getIcon());
            button.setTooltip(new Tooltip(command.getLabel()));
            button.setOnAction(event -> command.execute(provider.provide()));

            return button;
        }
    }

    interface CommandProvider<T> {
        T provide();
    }
}
