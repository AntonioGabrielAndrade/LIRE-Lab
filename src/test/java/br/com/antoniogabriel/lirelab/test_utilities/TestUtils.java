package br.com.antoniogabriel.lirelab.test_utilities;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.embed.swing.JFXPanel;
import org.apache.commons.io.FileUtils;
import org.testfx.api.FxRobot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class TestUtils {

    public static void startJavaFX() {
        new JFXPanel();
    }

    public static void runOnFxThreadAndWait(Runnable runnable) {
        startJavaFX();
        new FxRobot().interact(runnable);
    }

    public static void deleteWorkDirectory(PathResolver resolver) {
        try {
            File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection collection(String name, String imagesPath, Feature... features) {
        try {
            Collection collection = new Collection(name);
            collection.setImagesDirectory(imagesPath);
            collection.setFeatures(asList(features));

            if(Files.isDirectory(Paths.get(imagesPath))) {
                ArrayList<String> paths = net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(imagesPath), true);

                ArrayList<Image> images = new ArrayList<>();
                for (String path : paths) {
                    images.add(new Image(path, path));
                }
                collection.setImages(images);
            }

            return collection;
        } catch (IOException e) {
            throw new RuntimeException("Error creating collection", e);
        }
    }

}
