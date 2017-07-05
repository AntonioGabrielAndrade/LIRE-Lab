package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

class CollectionGridPageFactory implements Callback<Integer, Node> {

    private final Collection collection;
    private final int pageSize;
    private final ImageClickHandler clickHandler;

    public CollectionGridPageFactory(Collection collection,
                                     int pageSize,
                                     ImageClickHandler clickHandler) {

        this.collection = collection;
        this.pageSize = pageSize;
        this.clickHandler = clickHandler;
    }

    @Override
    public Node call(Integer pageIndex) {
        return createPage(pageIndex);
    }

    private CollectionGrid createPage(int pageIndex) {
        try {

            CollectionGrid page = createCollectionGrid();

            int fromIndex = indexOfFirstImageInPage(pageIndex);
            int toIndex = indexOfLastImageInPage(pageIndex) + 1;

            page.setImages(imagesInRange(fromIndex, toIndex), clickHandler);

            return page;

        } catch (IOException e) {
            throw new LireLabException("Could not create grid", e);
        }
    }

    private int indexOfLastImageInPage(int pageIndex) {
        return lastPossibleIndexInPage(pageIndex) < collectionLastIndex() ?
                lastPossibleIndexInPage(pageIndex) :
                collectionLastIndex();
    }

    private int lastPossibleIndexInPage(int pageIndex) {
        return indexOfFirstImageInPage(pageIndex) + (pageSize - 1);
    }

    private int collectionLastIndex() {
        return collection.totalImages() - 1;
    }

    private int indexOfFirstImageInPage(int pageIndex) {
        return pageIndex * pageSize;
    }

    private List<Image> imagesInRange(int fromIndex, int toIndex) {
        return collection.getImagesInRange(fromIndex, toIndex);
    }

    protected CollectionGrid createCollectionGrid() {
        return new CollectionGrid();
    }
}
