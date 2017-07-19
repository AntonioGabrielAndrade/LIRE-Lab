package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.controlsfx.control.PopOver;

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

    public void setPopOver(ImageView imageView, Image image) {
        PopOver popOver = new PopOver();

        VBox root = new VBox();
        root.setSpacing(3);
        root.setPadding(new Insets(10));

        root.getChildren().add(new Label(" image: " + image.getImageName()));

        if(image.getPosition() != -1)
            root.getChildren().add(new Label(" position: " + image.getPosition()));

        if(image.getScore() != -1)
            root.getChildren().add(new Label(" score: " + image.getScore()));

        popOver.setContentNode(root);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        imageView.hoverProperty().addListener((observable, wasHovering, isHovering) -> {
            if(isHovering && !popOver.isShowing()) {
                popOver.show(imageView);
            } else if(popOver.isShowing()) {
                popOver.hide();
            }
        });
    }
}
