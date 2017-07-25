package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.Command;
import br.com.antoniogabriel.lirelab.app.CommandTriggerFactory;
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
