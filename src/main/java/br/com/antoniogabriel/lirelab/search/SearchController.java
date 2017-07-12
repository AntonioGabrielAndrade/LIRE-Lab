package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.fxml.FXML;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchController {

    @FXML private SingleImageGrid queryGrid;
    @FXML private PaginatedCollectionGrid outputGrid;
    @FXML private StatusBar statusBar;

    private CollectionService service;

    @Inject
    public SearchController(CollectionService service) {
        this.service = service;
    }

    public void startSearchSession(Collection collection, Feature feature) {
        queryGrid.clear();
        outputGrid.setCollection(collection, new SetImageToGridClickHandler(queryGrid));
        queryGrid.setOnChange(new QueryChangeListener(this, collection, feature));
        setStatusMessage(collection, feature);
    }

    public void runQuery(Collection collection, Feature feature, Image queryImage) {
        RunQueryTask queryTask = createQueryTask(collection, feature, queryImage);
        statusBar.bindProgressTo(queryTask);
        queryTask.addValueListener((observable, oldValue, newValue) -> {
            outputGrid.setCollection(newValue, new SetImageToGridClickHandler(queryGrid));
        });
        new Thread(queryTask).start();
    }

    protected RunQueryTask createQueryTask(Collection collection, Feature feature, Image queryImage) {
        return new RunQueryTask(service, collection, feature, queryImage);
    }

    private void setStatusMessage(Collection collection, Feature feature) {
        statusBar.setSearchStatusInfo(collection, feature);
    }

}
