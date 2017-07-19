package br.com.antoniogabriel.lirelab.app;

import javafx.scene.control.ContextMenu;

import java.util.List;

public class CollectionTreeContextMenuFactory {

    private List<Command<Void>> commands;
    private CommandTriggerFactory<Void> triggerFactory = new CommandTriggerFactory<>();

    public CollectionTreeContextMenuFactory(List<Command<Void>> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu() {

        ContextMenu contextMenu = new ContextMenu();

        for (Command<Void> command : commands) {
            contextMenu.getItems().add(triggerFactory.createMenuItem(command, () -> null));
        }

        return contextMenu;
    }
}
