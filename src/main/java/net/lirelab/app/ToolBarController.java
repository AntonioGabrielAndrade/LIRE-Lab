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

package net.lirelab.app;

import net.lirelab.collection.Collection;
import net.lirelab.collection.CollectionService;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
    private CommandTriggerFactory<Void> commandTriggerFactory;
    private CollectionService collectionService;

    @Inject
    public ToolBarController(ApplicationCommands applicationCommands,
                             CommandTriggerFactory commandTriggerFactory,
                             CollectionService collectionService) {

        this.applicationCommands = applicationCommands;
        this.commandTriggerFactory = commandTriggerFactory;
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

    public ObservableValue<Collection> selectedCollectionProperty() {
        return searchCollectionComboBox.valueProperty();
    }

    private void setSearchComboBoxCollections() {
        searchCollectionComboBox.setItems(collectionService.getCollections());
        searchCollectionComboBox.setCommand(applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.SEARCH));
    }

    private void initLeftToolBar() {
        leftToolBar.getChildren().clear();
        List<Command<Void>> leftToolBarCommands = applicationCommands.getLeftToolBarCommands();
        for (Command<Void> command : leftToolBarCommands) {
            Button button = commandTriggerFactory.createButton(command, () -> null);
            button.setId("toolbar-" + command.getNodeId());
            leftToolBar.getChildren().add(button);
        }

    }

    private void initRightTooBar() {
        rightToolBar.getChildren().clear();
        List<Command<Void>> commands = applicationCommands.getRightToolBarCommands();
        for (Command<Void> command : commands) {
            Button button = commandTriggerFactory.createButton(command, () -> null);
            button.setId("toolbar-" + command.getNodeId());
            rightToolBar.getChildren().add(button);
        }
        rightToolBar.getChildren().add(searchCollectionComboBox);
    }
}
