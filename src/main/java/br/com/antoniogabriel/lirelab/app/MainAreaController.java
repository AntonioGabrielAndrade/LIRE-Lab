package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import net.semanticmetadata.lire.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class MainAreaController {

    @FXML
    private BorderPane centerPane;

    @Inject
    private PathResolver resolver;

    public void showCollectionImages(Collection collection) {
        try {
            FlowPane flowPane = createFlowPane();
            List<String> thumbs = getThumbnailsPaths(collection);

            for (String thumb : thumbs) {
                if (fileExists(thumb)) {

                    ImageView imageView = createImageView(thumb);

                    flowPane.getChildren().add(imageView);
                }
            }

            getCenterPane().setCenter(flowPane);
        } catch (FileNotFoundException e) {
            throw new LireLabException("Could not show images", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BorderPane getCenterPane() {
        return centerPane;
    }

    @NotNull
    protected ImageView createImageView(String thumb) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(thumb));
        ImageView imageView = new ImageView(image);

        String id = Paths.get(thumb).getFileName().toString();

        id = id
                .replace(".thumbnail", "")
                .replace(".jpg", "");

        imageView.setId(id);
        return imageView;
    }

    protected boolean fileExists(String thumb) {
        return Files.exists(Paths.get(thumb));
    }

    protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), true);
    }

    @NotNull
    protected FlowPane createFlowPane() {
        return new FlowPane();
    }
}
