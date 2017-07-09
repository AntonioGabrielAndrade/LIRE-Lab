package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.ImageChangeListener;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchViewController {

    @FXML private SingleImageGrid queryGrid;
    @FXML private PaginatedCollectionGrid collectionGrid;
    @FXML private StatusBar statusBar;

    private CollectionService service;

    @Inject
    public SearchViewController(CollectionService service) {
        this.service = service;
    }

    public void startSearchSession(Collection collection, Feature feature) {
        queryGrid.clear();
        collectionGrid.setCollection(collection, new SetImageToGridClickHandler(queryGrid));
        queryGrid.setOnChange(new ImageChangeListenerImpl(this, collection, feature));
        setStatusMessage(collection, feature);
    }

    public void runQuery(Collection collection, Feature feature, Image queryImage) {
        RunQueryTask queryTask = createQueryTask(collection, feature, queryImage);
        statusBar.bindProgressTo(queryTask);
        queryTask.addValueListener((observable, oldValue, newValue) -> {
            collectionGrid.setCollection(newValue, new SetImageToGridClickHandler(queryGrid));
        });
        new Thread(queryTask).start();
    }

    protected SearchViewController.RunQueryTask createQueryTask(Collection collection, Feature feature, Image queryImage) {
        return new RunQueryTask(service, collection, feature, queryImage);
    }

    private void setStatusMessage(Collection collection, Feature feature) {
        statusBar.setSearchStatusInfo(collection, feature);
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
        private SearchViewController controller;

        public ImageChangeListenerImpl(SearchViewController controller,
                                       Collection collection,
                                       Feature feature) {

            this.controller = controller;
            this.collection = collection;
            this.feature = feature;
        }

        @Override
        public void changed(Image queryImage) {
            controller.runQuery(collection, feature, queryImage);
        }
    }

    public static class RunQueryTask extends Task<Collection> {

        private final CollectionService service;
        private final Collection collection;
        private final Feature feature;
        private final Image queryImage;

        public RunQueryTask(CollectionService service,
                            Collection collection,
                            Feature feature,
                            Image queryImage) {

            this.service = service;
            this.collection = collection;
            this.feature = feature;
            this.queryImage = queryImage;
        }

        @Override
        protected Collection call() throws Exception {
            return service.runQuery(collection, feature, queryImage);
        }

        public void addValueListener(ChangeListener<Collection> listener) {
            valueProperty().addListener(listener);
        }
    }
}
