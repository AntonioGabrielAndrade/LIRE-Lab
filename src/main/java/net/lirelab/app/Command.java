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

import net.lirelab.custom.TangoIconWrapper;
import javafx.scene.Node;

public class Command<T> {

    private final String label;
    private final String nodeId;
    private final CommandAction action;
    private final String iconDescription;

    public Command(String label,
                   String iconDescription,
                   String nodeId,
                   CommandAction<T> action) {

        this.label = label;
        this.iconDescription = iconDescription;
        this.nodeId = nodeId;
        this.action = action;
    }

    public void execute(T param) {
        action.execute(param);
    }

    public String getLabel() {
        return label;
    }

    public Node getIcon() {
        return iconDescription.isEmpty() ?
                null :
                new TangoIconWrapper(iconDescription);
    }

    public String getNodeId() {
        return nodeId;
    }
}
