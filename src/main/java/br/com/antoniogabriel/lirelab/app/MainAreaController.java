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
            FlowPane flowPane = new FlowPane();
            List<String> thumbs = FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), true);

            System.out.println(thumbs);

            for (String thumb : thumbs) {
                if (Files.exists(Paths.get(thumb))) {

                    Image image = new Image(new FileInputStream(thumb));
                    ImageView imageView = new ImageView(image);

                    String id = Paths.get(thumb).getFileName().toString();

                    id = id
                            .replace(".thumbnail", "")
                            .replace(".jpg", "");

                    imageView.setId(id);

                    flowPane.getChildren().add(imageView);

                    System.out.println(imageView.getId());
                }
            }

            centerPane.setCenter(flowPane);
        } catch (FileNotFoundException e) {
            throw new LireLabException("Could not show images", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
