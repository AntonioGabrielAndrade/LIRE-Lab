package br.com.antoniogabriel.lirelab.app;

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

    public MenuItem createMenuItem(Command<E> command, CommandArgProvider<E> provider) {
        MenuItem item = new MenuItem(command.getLabel());

        item.setGraphic(command.getIcon());
        item.setOnAction(event -> command.execute(provider.provide()));

        return item;
    }
}
