package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.scene.control.TreeItem;
import org.kordamp.ikonli.javafx.FontIcon;

import java.nio.file.Paths;

class TreeItemBuilder {

    public TreeItem<String> createItem(Collection collection) {
        TreeItem<String> item = new TreeItem<>(collection.getName());
        item.setGraphic(new FontIcon("fa-folder"));
        return item;
    }

    public TreeItem<String> createItem(String imagePath) {
        String name = Paths.get(imagePath).getFileName().toString();
        TreeItem<String> item = new TreeItem<>(name);
        item.setGraphic(new FontIcon("fa-file-image-o"));
        return item;
    }
}
