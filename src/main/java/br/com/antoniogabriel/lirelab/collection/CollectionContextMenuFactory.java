package br.com.antoniogabriel.lirelab.collection;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

public class CollectionContextMenuFactory {


    private List<Command<Collection>> commands;

    public CollectionContextMenuFactory(List<Command<Collection>> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu(Collection collection) {

        ContextMenu contextMenu = new ContextMenu();

        for (Command<Collection> command : commands) {
            MenuItem item = new MenuItem(command.getLabel());

            item.setGraphic(command.getIcon());

            item.setOnAction(event -> command.execute(collection));

            contextMenu.getItems().add(item);
        }

        return contextMenu;
    }
}
