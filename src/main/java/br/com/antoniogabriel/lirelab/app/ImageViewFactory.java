package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ImageViewFactory {

    public ImageView create(String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        ImageView imageView = new ImageView(image);

        String id = Paths.get(path).getFileName().toString();

        id = id
                .replace(".thumbnail", "")
                .replace(".jpg", "");

        imageView.setId(id);
        return imageView;
    }
}
