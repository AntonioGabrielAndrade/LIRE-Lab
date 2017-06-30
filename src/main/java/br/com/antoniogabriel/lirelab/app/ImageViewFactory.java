package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ImageViewFactory {

    public ImageView create(String path) throws FileNotFoundException {
        ImageView imageView = createImageView(path);
        String id = createId(path);
        imageView.setId(id);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    private String createId(String path) {
        String filename = getFilename(path);
        String id = removeExtensions(filename, ".thumbnail", ".jpg");

        return id;
    }

    private String removeExtensions(String filename, String... extensions) {
        for (String extension : extensions) {
            filename = filename.replace(extension, "");
        }
        return filename;
    }

    private String getFilename(String path) {
        return Paths.get(path).getFileName().toString();
    }

    private ImageView createImageView(String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        return new ImageView(image);
    }
}
