package br.com.antoniogabriel.lirelab.custom.paginatedcollectiongrid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.DisplayImageDialogHandler;
import br.com.antoniogabriel.lirelab.custom.collectiongrid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PaginatedCollectionGrid extends StackPane {

    private static final String PAGINATED_COLLECTION_GRID_FXML = "paginated-collection-grid.fxml";
    private static final int DEFAULT_PAGE_SIZE = 120;

    private PageFactoryProvider pageFactoryProvider = new PageFactoryProvider();

    private Collection collection;
    private ImageClickHandler handler;

    @FXML private Pagination pagination;

    private int pageSize = DEFAULT_PAGE_SIZE;

    public PaginatedCollectionGrid() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PAGINATED_COLLECTION_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCollection(Collection collection) throws IOException {
        setCollection(collection, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setCollection(Collection collection, ImageClickHandler handler) throws IOException {
        this.collection = collection;
        this.handler = handler;

        calcPageCount(collection);
        setPageFactory(collection, pageSize, handler);
    }

    private void calcPageCount(Collection collection) {
        int pageCount = divideAndCeil(collection.getImages().size(), pageSize);
        pagination.setPageCount(pageCount);
    }

    private int divideAndCeil(int a, int b) {
        return a / b + ((a % b == 0) ? 0 : 1);
    }

    private void setPageFactory(Collection collection, int pageSize, ImageClickHandler handler) {
        pagination.setPageFactory(
                pageFactoryProvider.getPageFactory(collection, pageSize, handler)
        );
    }

    public Collection getCollection() {
        return collection;
    }

}
