package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.exception.LireLabException;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

class CollectionGridPageFactory implements Callback<Integer, Node> {

    private final List<Image> images;
    private final int pageSize;
    private final ImageClickHandler clickHandler;

    public CollectionGridPageFactory(List<Image> images,
                                     int pageSize,
                                     ImageClickHandler clickHandler) {

        this.images = images;
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
        return images.size() - 1;
    }

    private int indexOfFirstImageInPage(int pageIndex) {
        return pageIndex * pageSize;
    }

    private List<Image> imagesInRange(int fromIndex, int toIndex) {
        return images.subList(fromIndex, toIndex);
    }

    protected CollectionGrid createCollectionGrid() {
        return new CollectionGrid();
    }
}
