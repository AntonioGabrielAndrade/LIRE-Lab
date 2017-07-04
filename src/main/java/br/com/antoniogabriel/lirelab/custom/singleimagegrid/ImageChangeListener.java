package br.com.antoniogabriel.lirelab.custom.singleimagegrid;

import br.com.antoniogabriel.lirelab.collection.Image;

@FunctionalInterface
public interface ImageChangeListener {
    void changed(Image newImage);
}
