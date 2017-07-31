/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        popOver.setAnimated(false);

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
