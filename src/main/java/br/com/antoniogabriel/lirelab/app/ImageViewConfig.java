package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class ImageViewConfig {

    public void bindImageHeight(ImageView imageView, Region container, int divider) {
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(container.heightProperty().divide(divider));
    }
}
