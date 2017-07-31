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

package br.com.antoniogabriel.lirelab.custom.image_dialog;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DialogActions {

    private Dialog dialog;

    public DialogActions(Dialog dialog) {
        this.dialog = dialog;
    }

    public void addButtonType(ButtonType buttonType) {
        dialog.getDialogPane().getButtonTypes().add(buttonType);
    }

    public void setDialogPaneId(String id) {
        dialog.getDialogPane().setId(id);
    }

    public void setContent(Node content) {
        dialog.getDialogPane().setContent(content);
    }

    public void setTitle(String title) {
        dialog.setTitle(title);
    }

    public void setResizable(boolean value) {
        dialog.setResizable(value);
    }
}
