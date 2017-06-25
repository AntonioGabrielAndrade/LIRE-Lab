package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import net.semanticmetadata.lire.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CollectionGrid extends StackPane {

    private static final String COLLECTION_GRID_FXML = "collection-grid.fxml";

    private PathResolver resolver;
    private Collection collection;
    private ImageGridBuilder imageGridBuilder;

    @FXML private StackPane root;

    public CollectionGrid(PathResolver resolver, ImageGridBuilder imageGridBuilder) {
        this.resolver = resolver;
        this.imageGridBuilder = imageGridBuilder;

        loadFXML();
    }

    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COLLECTION_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void setCollection(Collection collection) throws IOException {
        this.collection = collection;
        ImageGrid imageGrid = imageGridBuilder.build();
        imageGrid.setImages(getThumbnailsPaths(collection));
        root.getChildren().add(imageGrid);
    }

    public Collection getCollection() {
        return collection;
    }

    protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), true);
    }
}
