package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import javafx.scene.control.TreeItem;

import java.nio.file.Paths;

class TreeItemBuilder {

    public TreeItem<String> createItem(Collection collection) {
        TreeItem<String> item = new TreeItem<>(collection.getName());
        item.setGraphic(new TangoIconWrapper("places:folder"));
        return item;
    }

    public TreeItem<String> createItem(String imagePath) {
        String name = Paths.get(imagePath).getFileName().toString();
        TreeItem<String> item = new TreeItem<>(name);
        item.setGraphic(new TangoIconWrapper("mimetypes:image-x-generic"));
        return item;
    }
}
