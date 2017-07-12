package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import javafx.scene.input.MouseEvent;

class SetImageToGridClickHandler implements ImageClickHandler {

    private final SingleImageGrid grid;

    public SetImageToGridClickHandler(SingleImageGrid grid) {
        this.grid = grid;
    }

    @Override
    public void handle(Image image, MouseEvent event) {
        grid.setImage(image);
    }
}
