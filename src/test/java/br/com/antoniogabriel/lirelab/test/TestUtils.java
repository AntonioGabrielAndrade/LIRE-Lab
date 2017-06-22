package br.com.antoniogabriel.lirelab.test;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.embed.swing.JFXPanel;
import org.apache.commons.io.FileUtils;
import org.testfx.api.FxRobot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.util.Arrays.asList;

public class TestUtils {

    public static void startJavaFX() {
        new JFXPanel();
    }

    public static void runOnFXThread(Runnable runnable) {
        new FxRobot().interact(runnable);
    }

    public static void deleteWorkDirectory(PathResolver resolver) throws IOException {
        File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
        FileUtils.deleteDirectory(directory);
    }

    public static Collection collection(String name, String imagesPath, Feature... features) {
        Collection collection = new Collection(name);
        collection.setImagesDirectory(imagesPath);
        collection.setFeatures(asList(features));
        return collection;
    }
}
