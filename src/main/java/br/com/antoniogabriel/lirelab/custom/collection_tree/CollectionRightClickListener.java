package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.scene.input.MouseEvent;

@FunctionalInterface
public interface CollectionRightClickListener {
    void clicked(Collection collection, MouseEvent event);
}
