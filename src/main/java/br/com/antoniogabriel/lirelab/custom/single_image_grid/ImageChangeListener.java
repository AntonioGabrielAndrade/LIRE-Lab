package br.com.antoniogabriel.lirelab.custom.single_image_grid;

import br.com.antoniogabriel.lirelab.collection.Image;

@FunctionalInterface
public interface ImageChangeListener {
    void changed(Image newImage);
}
