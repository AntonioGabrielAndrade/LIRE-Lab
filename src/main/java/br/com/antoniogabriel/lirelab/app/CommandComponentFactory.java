package br.com.antoniogabriel.lirelab.app;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

class CommandComponentFactory<E> {

    public Button createButton(Command<E> command, CommandArgProvider<E> provider) {
        Button button = new Button();

        button.setGraphic(command.getIcon());
        button.setTooltip(new Tooltip(command.getLabel()));
        button.setOnAction(event -> command.execute(provider.provide()));

        return button;
    }
}
