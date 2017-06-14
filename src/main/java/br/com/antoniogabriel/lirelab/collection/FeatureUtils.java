package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FeatureUtils {

    public static ObservableList<ViewableFeature> toViewableFeatures(Feature... features) {
        return Arrays.stream(features)
                .map(f -> new ViewableFeature(f))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
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
