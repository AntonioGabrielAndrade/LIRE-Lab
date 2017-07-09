package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.scene.input.MouseEvent;

@FunctionalInterface
public interface ImageClickHandler {
    void handle(Image image, MouseEvent event);
}
