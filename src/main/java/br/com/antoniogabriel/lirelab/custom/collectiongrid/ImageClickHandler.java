package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.scene.input.MouseEvent;

@FunctionalInterface
public interface ImageClickHandler {
    void handle(Image image, MouseEvent event);
}
