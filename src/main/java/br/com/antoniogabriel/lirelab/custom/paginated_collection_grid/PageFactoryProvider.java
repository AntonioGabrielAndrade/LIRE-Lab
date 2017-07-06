package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.scene.Node;
import javafx.util.Callback;

class PageFactoryProvider {

    public Callback<Integer, Node> getPageFactory(Collection collection,
                                                  int pageSize,
                                                  ImageClickHandler imageClickHandler) {

        return new CollectionGridPageFactory(collection, pageSize, imageClickHandler);
    }
}
