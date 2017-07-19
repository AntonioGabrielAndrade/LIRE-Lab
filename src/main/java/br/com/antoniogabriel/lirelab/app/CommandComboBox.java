package br.com.antoniogabriel.lirelab.app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

import static javafx.collections.FXCollections.observableList;

public class CommandComboBox<T> extends HBox {

    private static final String COMMAND_COMBOBOX_FXML = "command-combobox.fxml";

    private ButtonCommandFactory<T> factory = new ButtonCommandFactory();

    @FXML private ComboBox<T> comboBox;
    @FXML private Button actionButton;

    private SimpleObjectProperty<Command<T>> commandProperty = new SimpleObjectProperty<>();

    public CommandComboBox() {
        loadFXML();
    }

    private Node updateButton() {
        return getChildren().set(1, factory.createButton(commandProperty.getValue(), () -> comboBox.getValue()));
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

    class ButtonCommandFactory<E> {

        public Button createButton(Command<E> command, CommandArgProvider<E> provider) {
            Button button = new Button();
            button.setId("toolbar-search-collection");

            button.setGraphic(command.getIcon());
            button.setTooltip(new Tooltip(command.getLabel()));
            button.setOnAction(event -> command.execute(provider.provide()));

            return button;
        }
    }

    interface CommandArgProvider<A> {
        A provide();
    }
}
