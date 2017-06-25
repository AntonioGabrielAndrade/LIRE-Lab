package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.scene.layout.StackPane;
import net.semanticmetadata.lire.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CollectionGrid extends StackPane {

    private PathResolver resolver;
    private Collection collection;
    private ImageGridBuilder imageGridBuilder;

    public CollectionGrid(PathResolver resolver, ImageGridBuilder imageGridBuilder) {
        super();
        this.resolver = resolver;
        this.imageGridBuilder = imageGridBuilder;
    }

    public void setCollection(Collection collection) throws IOException {
        this.collection = collection;
        ImageGrid imageGrid = imageGridBuilder.build();
        imageGrid.setImages(getThumbnailsPaths(collection));
        getChildren().add(imageGrid);
    }

    public Collection getCollection() {
        return collection;
    }

    protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), true);
    }
}
