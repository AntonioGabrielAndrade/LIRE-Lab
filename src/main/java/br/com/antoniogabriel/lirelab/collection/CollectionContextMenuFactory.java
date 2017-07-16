package br.com.antoniogabriel.lirelab.collection;

import javafx.concurrent.Task;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class CollectionContextMenuFactory {

    private CollectionService collectionService;

    public CollectionContextMenuFactory(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    public ContextMenu createContextMenu(Collection collection) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = createDeleteItem(collection);

        contextMenu.getItems().add(deleteItem);
        return contextMenu;
    }

    private MenuItem createDeleteItem(Collection collection) {
        MenuItem deleteItem = new MenuItem("Delete collection");
        deleteItem.setOnAction(ev -> {
            Task<Void> deletion = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    collectionService.deleteCollection(collection);
                    return null;
                }
            };
            new Thread(deletion).start();
        });
        return deleteItem;
    }

}
