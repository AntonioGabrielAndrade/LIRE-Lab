package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static br.com.antoniogabriel.lirelab.collection.FeatureUtils.getSelectedFeaturesFrom;
import static br.com.antoniogabriel.lirelab.collection.FeatureUtils.noFeatureIsSelectedIn;
import static br.com.antoniogabriel.lirelab.collection.FeatureUtils.toViewableFeatures;
import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.FCTH;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FeatureUtilsTest {

    @Test
    public void shouldConvertFeaturesIntoViewableFeatures() throws Exception {
        ObservableList<ViewableFeature> result = toViewableFeatures(CEDD, TAMURA);

        assertThat(firstFeatureOf(result), is(CEDD));
        assertThat(secondFeatureOf(result), is(TAMURA));
    }


    @Test
    public void shouldGetSelectedFeatures() throws Exception {
        ArrayList<Feature> selected = getSelectedFeaturesFrom(
                                                someFeatures(
                                                        selected(CEDD),
                                                        selected(TAMURA),
                                                        notSelected(FCTH)
                                                ));

        assertThat(selected.size(), is(2));
        assertTrue(selected.contains(CEDD));
        assertTrue(selected.contains(TAMURA));
    }

    @Test
    public void shouldBindNoSelectedFeatureValueOfViewableFeatures() throws Exception {
        ObservableList<ViewableFeature> features = someFeatures(
                                                        notSelected(CEDD),
                                                        notSelected(TAMURA)
                                                        );

        BooleanBinding binding = noFeatureIsSelectedIn(features);

        assertThat(valueOf(binding), is(true));
        selectFirstItemOf(features);
        assertThat(valueOf(binding), is(false));
    }

    private void selectFirstItemOf(ObservableList<ViewableFeature> features) {
        features.get(0).setSelected(true);
    }

    private boolean valueOf(BooleanBinding binding) {
        return binding.get();
    }

    private ObservableList<ViewableFeature> someFeatures(ViewableFeature... features) {
        return FXCollections.observableArrayList(features);
    }

    private ViewableFeature notSelected(Feature feature) {
        return getViewableFeature(feature, false);
    }

    private ViewableFeature selected(Feature feature) {
        return getViewableFeature(feature, true);
    }

    private ViewableFeature getViewableFeature(Feature feature, boolean selected) {
        ViewableFeature viewableFeature = new ViewableFeature(feature);
        viewableFeature.setSelected(selected);
        return viewableFeature;
    }

    private Feature secondFeatureOf(ObservableList<ViewableFeature> list) {
        return list.get(1).getFeature();
    }

    private Feature firstFeatureOf(ObservableList<ViewableFeature> list) {
        return list.get(0).getFeature();
    }
}
