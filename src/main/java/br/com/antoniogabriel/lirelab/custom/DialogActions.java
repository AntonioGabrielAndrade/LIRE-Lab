package br.com.antoniogabriel.lirelab.custom;

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
}
