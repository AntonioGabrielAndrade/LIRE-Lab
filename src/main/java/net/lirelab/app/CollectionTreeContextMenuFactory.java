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

import javafx.scene.control.ContextMenu;

import java.util.List;

public class CollectionTreeContextMenuFactory {

    private List<Command<Void>> commands;
    private CommandTriggerFactory<Void> triggerFactory = new CommandTriggerFactory<>();

    public CollectionTreeContextMenuFactory(List<Command<Void>> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu() {

        ContextMenu contextMenu = new ContextMenu();

        for (Command<Void> command : commands) {
            contextMenu.getItems().add(triggerFactory.createMenuItem(command, () -> null));
        }

        return contextMenu;
    }
}
