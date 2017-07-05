package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.IOException;

class CollectionGridPageFactory implements Callback<Integer, Node> {

    private final Collection collection;
    private final int pageSize;
    private final ImageClickHandler imageClickHandler;

    public CollectionGridPageFactory(Collection collection,
                                     int pageSize,
                                     ImageClickHandler imageClickHandler) {

        this.collection = collection;
        this.pageSize = pageSize;
        this.imageClickHandler = imageClickHandler;
    }

    @Override
    public Node call(Integer pageIndex) {
        return createPage(pageIndex);
    }

    private CollectionGrid createPage(int pageIndex) {
        try {

            CollectionGrid collectionGrid = new CollectionGrid();

            int lastIndex = collection.getImages().size() - 1;
            int fromIndex = pageIndex * pageSize;
            int toIndex = (fromIndex + pageSize) > lastIndex ?
                    lastIndex + 1 : (fromIndex + pageSize);

            collectionGrid.setImages(
                    collection.getImages().subList(fromIndex, toIndex),
                    imageClickHandler);

            return collectionGrid;

        } catch (IOException e) {
            throw new LireLabException("Could not create grid", e);
        }
    }
}
