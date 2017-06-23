package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import net.semanticmetadata.lire.utils.FileUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class MainAreaController {

    @FXML
    private BorderPane centerPane;

    private PathResolver resolver;

    private ImageViewFactory imageViewFactory;

    @Inject
    public MainAreaController(PathResolver resolver, ImageViewFactory imageViewFactory) {
        this.resolver = resolver;
        this.imageViewFactory = imageViewFactory;
    }

    public void showCollectionImages(Collection collection) {
        try {
            FlowPane flowPane = createFlowPane();
            List<String> thumbs = getThumbnailsPaths(collection);

            for (String thumb : thumbs) {
                if (fileExists(thumb)) {

                    ImageView imageView = imageViewFactory.create(thumb);

                    flowPane.getChildren().add(imageView);
                }
            }

            getCenterPane().setCenter(flowPane);

        } catch (IOException e) {
            throw new LireLabException("Could not show collections", e);
        }
    }

    protected BorderPane getCenterPane() {
        return centerPane;
    }

    protected boolean fileExists(String thumb) {
        return Files.exists(Paths.get(thumb));
    }

    protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), true);
    }

    protected FlowPane createFlowPane() {
        return new FlowPane();
    }
}
