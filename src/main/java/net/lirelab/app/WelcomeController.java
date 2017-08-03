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
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.CREATE);
        Button button = commandTriggerFactory.createButton(createCommand, () -> null);
        createCollectionButtonPane.getChildren().add(button);
    }

    private void setupSearchCollectionButton() {
        Command<Collection> createCommand = applicationCommands.getCollectionCommand(ApplicationCommands.CollectionCommand.SEARCH);
        Button button = commandTriggerFactory.createButton(createCommand, () -> null);
        button.setDisable(true);
        searchCollectionButtonPane.getChildren().add(button);
    }
}
