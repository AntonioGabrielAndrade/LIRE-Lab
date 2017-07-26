package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.util.Callback;

import java.util.List;

class PageFactoryProvider {

    public Callback<Integer, Node> getPageFactory(List<Image> images,
                                                  int pageSize,
                                                  ImageClickHandler imageClickHandler,
                                                  DoubleProperty gridGap,
                                                  DoubleProperty imageHeight) {

        return new CollectionGridPageFactory(images, pageSize, imageClickHandler, gridGap, imageHeight);
    }
}
