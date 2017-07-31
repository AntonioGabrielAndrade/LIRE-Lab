/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.antoniogabriel.lirelab.app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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

    private CommandTriggerFactory<T> factory = new CommandTriggerFactory();

    @FXML private ComboBox<T> comboBox;
    @FXML private Button actionButton;

    private SimpleObjectProperty<Command<T>> commandProperty = new SimpleObjectProperty<>();

    public CommandComboBox() {
        loadFXML();
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

    private Node updateButton() {
        Button button = factory.createButton(commandProperty.getValue(), () -> comboBox.getValue());
        button.setId("toolbar-search-collection");
        setupButtonDisableProperty(button);

        return getChildren().set(1, button);
    }

    private void setupButtonDisableProperty(Button button) {
        button.disableProperty().bind(comboBox.getSelectionModel().selectedItemProperty().isNull());
    }

    public void setItems(List<T> items) {
        comboBox.setItems(observableList(items));
        comboBox.getSelectionModel().select(0);
    }

    public void setSelectedItem(T item) {
        comboBox.getSelectionModel().select(item);
    }

    public T getSelectedItem() {
        return comboBox.getValue();
    }

    public ObservableValue<T> valueProperty() {
        return comboBox.valueProperty();
    }

    public Command<T> getCommand() {
        return commandProperty.get();
    }

    public void setCommand(Command<T> command) {
        this.commandProperty.set(command);
        updateButton();
    }

}
