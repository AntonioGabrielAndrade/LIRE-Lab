package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import javafx.scene.Node;
import javafx.util.Callback;

class PageFactoryProvider {

    public Callback<Integer, Node> getPageFactory(Collection collection,
                                                  int pageSize,
                                                  ImageClickHandler imageClickHandler) {

        return new CollectionGridPageFactory(collection, pageSize, imageClickHandler);
    }
}
