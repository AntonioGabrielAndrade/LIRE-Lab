package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

public class CollectionContextMenuFactory {


    private List<CollectionCommand> commands;

    public CollectionContextMenuFactory(List<CollectionCommand> commands) {
        this.commands = commands;
    }

    public ContextMenu createContextMenu(Collection collection) {

        ContextMenu contextMenu = new ContextMenu();

        for (CollectionCommand command : commands) {
            MenuItem item = new MenuItem(command.getLabel());

            if(!command.getIconDescription().isEmpty()) {
                TangoIconWrapper icon = new TangoIconWrapper(command.getIconDescription());
                item.setGraphic(icon);
            }

            item.setOnAction(event -> command.execute(collection));

            contextMenu.getItems().add(item);
        }

        return contextMenu;
    }
}
