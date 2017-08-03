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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML private Menu fileMenu;
    @FXML private Menu searchMenu;
    @FXML private Menu helpMenu;

    private ApplicationCommands applicationCommands;
    private CommandTriggerFactory commandTriggerFactory;

    private SimpleObjectProperty<Collection> selectedCollection = new SimpleObjectProperty<>(null);

    @Inject
    public MenuBarController(ApplicationCommands applicationCommands, CommandTriggerFactory commandTriggerFactory) {
        this.applicationCommands = applicationCommands;
        this.commandTriggerFactory = commandTriggerFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFileMenu();
        setupHelpMenu();
    }

    public void bindSelectedCollectionTo(ObservableValue<Collection> value) {
        selectedCollection.bind(value);
    }

    private void setupFileMenu() {
        List<Command<Void>> commands = applicationCommands.getFileMenuCommands();
        for (Command<Void> command : commands) {
            MenuItem menuItem = commandTriggerFactory.createMenuItem(command, () -> null);
            menuItem.setId(command.getNodeId());
            fileMenu.getItems().add(menuItem);
        }
    }

    private void setupHelpMenu() {
        List<Command<Void>> commands = applicationCommands.getHelpMenuCommands();
        for (Command<Void> command : commands) {
            MenuItem menuItem = commandTriggerFactory.createMenuItem(command, () -> null);
            menuItem.setId(command.getNodeId());
            helpMenu.getItems().add(menuItem);
        }
    }
}
