package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class ImageViewConfig {

    public void bindImageHeight(ImageView imageView, Region container, double factor) {
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(container.heightProperty().multiply(factor));
    }

    public ImageView limitImageHeight(ImageView imageView, double limit) {

        double height = imageView.getImage().getHeight();

        if(height > limit) {
            imageView.setFitHeight(limit);
        } else {
            imageView.setFitHeight(height);
        }

        return imageView;
    }
}
