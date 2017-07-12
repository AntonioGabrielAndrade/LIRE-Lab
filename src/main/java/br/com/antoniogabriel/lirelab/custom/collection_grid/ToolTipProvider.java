package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ToolTipProvider {

    public void setToolTip(Node node, String tooltip) {
        Tooltip.install(node, new Tooltip(tooltip));
    }

    public void setToolTip(ImageView imageView, Image image) {
        String msg = "";
        msg += "image: " + image.getImageName() + "\n";

        if(image.getPosition() != -1)
            msg += "position: " + image.getPosition() + "\n";

        if(image.getScore() != -1)
            msg += "score: " + image.getScore() + "\n";

        Tooltip tooltip = new Tooltip(msg);
        tooltip.setFont(new Font("Arial", 16));
        tooltip.setStyle("-fx-background-color: aquamarine; -fx-text-fill: black");

        Tooltip.install(imageView, tooltip);
    }
}
