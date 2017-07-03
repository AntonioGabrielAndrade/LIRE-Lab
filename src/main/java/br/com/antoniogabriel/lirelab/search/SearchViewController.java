package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.imagegrid.ImageGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.io.IOException;

@Singleton
public class SearchViewController {

    @FXML private ImageGrid queryGrid;
    @FXML private CollectionGrid collectionGrid;

    public void startSearchSession(Collection collection, Feature feature) {
        try {

            collectionGrid.setCollection(collection, new SetImageToGridClickHandler(queryGrid));

//            queryGrid.setOnChange(() -> {
//                service.search(collection.getImages(), feature, queryGrid.getImages());
//            });

        } catch (IOException e) {
            throw new LireLabException("Could not show collection", e);
        }
    }

    class SetImageToGridClickHandler implements ImageClickHandler {

        private final ImageGrid grid;

        public SetImageToGridClickHandler(ImageGrid grid) {
            this.grid = grid;
        }

        @Override
        public void handle(Image image, MouseEvent event) {
            try {
                grid.clear();
                grid.addImage(image.getThumbnailPath());
            } catch (FileNotFoundException e) {
                throw new LireLabException("Could not add image to grid", e);
            }
        }
    }
}
