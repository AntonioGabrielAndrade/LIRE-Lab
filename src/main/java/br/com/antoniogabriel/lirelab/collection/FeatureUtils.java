package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FeatureUtils {

    public static ObservableList<ViewableFeature> toViewableFeatures(Feature... features) {
        ObservableList<ViewableFeature> result = FXCollections.observableArrayList();
        for (Feature feature : features) {
            result.add(new ViewableFeature(feature));
        }
        return result;
    }

    public static ArrayList<Feature> getSelectedFeaturesFrom(ObservableList<ViewableFeature> viewableFeatures) {
        return viewableFeatures.stream()
                .filter(v -> v.isSelected())
                .map(v -> v.getFeature())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static BooleanBinding noFeatureIsSelectedIn(ObservableList<ViewableFeature> viewableFeatures) {
        return viewableFeatures.stream()
                .map(v -> v.selectedProperty().not())
                .reduce(BooleanBinding::and)
                .get();
    }
}
