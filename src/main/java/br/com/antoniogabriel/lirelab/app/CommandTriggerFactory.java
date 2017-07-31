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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

public class CommandTriggerFactory<E> {

    public Button createButton(Command<E> command, CommandArgProvider<E> provider) {
        Button button = new Button();

        button.setGraphic(command.getIcon());
        button.setTooltip(new Tooltip(command.getLabel()));
        button.setOnAction(event -> command.execute(provider.provide()));

        return button;
    }

    public MenuItem createMenuItem(Command<E> command, CommandArgProvider<E> argProvider) {
        MenuItem item = new MenuItem(command.getLabel());

        item.setGraphic(command.getIcon());
        item.setOnAction(event -> command.execute(argProvider.provide()));
        item.setId(command.getNodeId());

        return item;
    }

    public MenuItem createMenuItem(Command<E> command, SimpleObjectProperty<E> argProvider) {
        MenuItem item = new MenuItem(command.getLabel());

        argProvider.addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                item.setText(command.getLabel() + " [" + argProvider.getValue() + "]");
            }
        });

        item.setGraphic(command.getIcon());
        item.setOnAction(event -> command.execute(argProvider.getValue()));
        item.setId(command.getNodeId());

        return item;
    }
}
