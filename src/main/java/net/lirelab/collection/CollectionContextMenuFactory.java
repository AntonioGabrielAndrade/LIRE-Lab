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

package net.lirelab.collection;

import net.lirelab.app.Command;
import net.lirelab.app.CommandTriggerFactory;
import javafx.scene.control.ContextMenu;

import java.util.List;

public class CollectionContextMenuFactory {

    private List<Command<Collection>> commands;
    private CommandTriggerFactory<Collection> triggerFactory = new CommandTriggerFactory<>();

    public CollectionContextMenuFactory(List<Command<Collection>> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu(Collection collection) {

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setId("collection-context-menu");

        for (Command<Collection> command : commands) {
            contextMenu.getItems().add(triggerFactory.createMenuItem(command, () -> collection));
        }

        return contextMenu;
    }
}
