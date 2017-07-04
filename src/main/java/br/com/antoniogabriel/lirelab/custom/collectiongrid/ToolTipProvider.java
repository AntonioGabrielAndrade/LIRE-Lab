package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class ToolTipProvider {
    public void setToolTip(Node node, String tooltip) {
        Tooltip.install(node, new Tooltip(tooltip));
    }
}
