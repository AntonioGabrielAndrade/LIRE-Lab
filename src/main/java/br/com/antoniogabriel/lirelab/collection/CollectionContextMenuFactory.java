package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.Command;
import br.com.antoniogabriel.lirelab.app.CommandComponentFactory;
import javafx.scene.control.ContextMenu;

import java.util.List;

public class CollectionContextMenuFactory {

    private List<Command<Collection>> commands;
    private CommandComponentFactory<Collection> componentFactory = new CommandComponentFactory<>();

    public CollectionContextMenuFactory(List<Command<Collection>> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu(Collection collection) {

        ContextMenu contextMenu = new ContextMenu();

        for (Command<Collection> command : commands) {
            contextMenu.getItems().add(componentFactory.createMenuItem(command, () -> collection));
        }

        return contextMenu;
    }
}
