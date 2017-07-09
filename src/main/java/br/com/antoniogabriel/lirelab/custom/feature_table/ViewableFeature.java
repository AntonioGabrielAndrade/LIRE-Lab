package br.com.antoniogabriel.lirelab.custom.feature_table;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewableFeature {
    private final SimpleBooleanProperty selected;
    private final SimpleStringProperty name;
    private final Feature feature;

    public ViewableFeature(Feature feature) {
        this.selected = new SimpleBooleanProperty(false);
        this.name = new SimpleStringProperty(feature.getFeatureName());
        this.feature = feature;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Feature getFeature() {
        return feature;
    }
}
