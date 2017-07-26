package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.DisplayImageDialogHandler;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Pagination;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaginatedCollectionGrid extends BorderPane {

    private static final String PAGINATED_COLLECTION_GRID_FXML = "paginated-collection-grid.fxml";
    private static final int DEFAULT_PAGE_SIZE = 120;

    private PageFactoryProvider pageFactoryProvider = new PageFactoryProvider();

    @FXML private Pagination pagination;
    @FXML private Spinner<Integer> pageSizeSpinner;

    private List<Image> images = new ArrayList<>();
    private ImageClickHandler handler = (image, event) -> {};

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

        setupPageSizeSpinner();
    }

    private void setupPageSizeSpinner() {
        pageSizeSpinner.getValueFactory().setValue(DEFAULT_PAGE_SIZE);

        pageSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!images.isEmpty())
                setCollection(images, handler);
        });
    }

    public void setPageSize(int pageSize) {
        pageSizeSpinner.getValueFactory().setValue(pageSize);
    }

    public int getPageSize() {
        return pageSizeSpinner.getValue();
    }

    public void clear() {
        pagination.setPageFactory(null);
    }

    public void setCollection(Collection collection) throws IOException {
        setCollection(collection, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setCollection(Collection collection, ImageClickHandler handler) {
        setCollection(collection.getImages(), handler);
    }

    public void setCollection(List<Image> images, ImageClickHandler handler) {
        this.images = images;
        this.handler = handler;
        setupMaxPageSize(images);

        calcPageCount(images, getPageSize());
        setPageFactory(images, getPageSize(), handler);
    }

    private void setupMaxPageSize(List<Image> images) {
        ((SpinnerValueFactory.IntegerSpinnerValueFactory)pageSizeSpinner.getValueFactory()).setMax(images.size());
    }

    private void calcPageCount(List<Image> images, int pageSize) {
        int numberOfImages = images.size();
        int pageCount = divideAndGetCeil(numberOfImages, pageSize);

        pagination.setPageCount(pageCount);
    }

    private int divideAndGetCeil(int a, int b) {
        return a / b + ((a % b == 0) ? 0 : 1);
    }

    private void setPageFactory(List<Image> images, int pageSize, ImageClickHandler handler) {
        pagination.setPageFactory(
                pageFactoryProvider.getPageFactory(images, pageSize, handler)
        );
    }
}
