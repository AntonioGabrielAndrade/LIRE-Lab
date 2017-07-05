package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.NoSuchElementException;

public class ChooseFeatureDialog extends ChoiceDialog<Feature> {

    public ChooseFeatureDialog(List<Feature> features) {
        super(features.get(0), features);

        setTitle("Select Feature");
        setHeaderText("Choose Feature to use in query");
        setGraphic(null);
        getDialogPane().setMinWidth(500);
        getDialogPane().setId("choose-feature-dialog");
    }

    public Feature showAndGetFeature() {
        try {
            return showAndWait().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
