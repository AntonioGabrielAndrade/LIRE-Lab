package br.com.antoniogabriel.lirelab.app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

import static javafx.collections.FXCollections.observableList;

public class CommandComboBox<T> extends HBox {

    private static final String COMMAND_COMBOBOX_FXML = "command-combobox.fxml";

    private CommandComponentFactory<T> factory = new CommandComponentFactory();

    @FXML private ComboBox<T> comboBox;
    @FXML private Button actionButton;

    private SimpleObjectProperty<Command<T>> commandProperty = new SimpleObjectProperty<>();

    public CommandComboBox() {
        loadFXML();
    }

    private Node updateButton() {
        Button button = factory.createButton(commandProperty.getValue(), () -> comboBox.getValue());
        button.setId("toolbar-search-collection");

        return getChildren().set(1, button);
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COMMAND_COMBOBOX_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setItems(List<T> items) {
        comboBox.getItems().clear();
        comboBox.setItems(observableList(items));
        comboBox.getSelectionModel().select(0);
    }

    public void setSelectedItem(T item) {
        comboBox.getSelectionModel().select(item);
    }

    public T getSelectedItem() {
        return comboBox.getValue();
    }

    public Command<T> getCommand() {
        return commandProperty.get();
    }

    public void setCommand(Command<T> command) {
        this.commandProperty.set(command);
        updateButton();
    }

}
