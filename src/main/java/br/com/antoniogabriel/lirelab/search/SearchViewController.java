package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.ImageChangeListener;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class SearchViewController {

    @FXML private SingleImageGrid queryGrid;
    @FXML private PaginatedCollectionGrid collectionGrid;

    private CollectionService service;

    @Inject
    public SearchViewController(CollectionService service) {
        this.service = service;
    }

    public void startSearchSession(Collection collection, Feature feature) {
        try {

            queryGrid.clear();
            collectionGrid.setCollection(collection, new SetImageToGridClickHandler(queryGrid));
            queryGrid.setOnChange(new ImageChangeListenerImpl(collection, feature, service, collectionGrid, queryGrid));

        } catch (IOException e) {
            throw new LireLabException("Could not show collection", e);
        }
    }

    static class SetImageToGridClickHandler implements ImageClickHandler {

        private final SingleImageGrid grid;

        public SetImageToGridClickHandler(SingleImageGrid grid) {
            this.grid = grid;
        }

        @Override
        public void handle(Image image, MouseEvent event) {
            grid.setImage(image);
        }
    }

    static class ImageChangeListenerImpl implements ImageChangeListener {

        private final Collection collection;
        private final Feature feature;
        private CollectionService service;
        private PaginatedCollectionGrid collectionGrid;
        private SingleImageGrid queryGrid;

        public ImageChangeListenerImpl(Collection collection,
                                       Feature feature,
                                       CollectionService service,
                                       PaginatedCollectionGrid collectionGrid,
                                       SingleImageGrid queryGrid) {

            this.collection = collection;
            this.feature = feature;
            this.service = service;
            this.collectionGrid = collectionGrid;
            this.queryGrid = queryGrid;
        }

        @Override
        public void changed(Image queryImage) {
            try {
                collectionGrid.setCollection(service.runQuery(collection, feature, queryImage),
                                        new SetImageToGridClickHandler(queryGrid));
            } catch (IOException e) {
                throw new LireLabException("Could not run query", e);
            }
        }
    }
}
