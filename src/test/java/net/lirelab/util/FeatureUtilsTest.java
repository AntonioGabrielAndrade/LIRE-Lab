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

package net.lirelab.util;

import net.lirelab.custom.feature_table.ViewableFeature;
import net.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FeatureUtilsTest {

    private FeatureUtils utils = new FeatureUtils();

    @Test
    public void shouldConvertFeaturesIntoViewableFeatures() throws Exception {
        ObservableList<ViewableFeature> result = utils.toViewableFeatures(Feature.CEDD, Feature.TAMURA);

        assertThat(firstFeatureOf(result), CoreMatchers.is(Feature.CEDD));
        assertThat(secondFeatureOf(result), CoreMatchers.is(Feature.TAMURA));
    }


    @Test
    public void shouldGetSelectedFeatures() throws Exception {
        ArrayList<Feature> selected = utils.getSelectedFeaturesFrom(
                                                someFeatures(
                                                        selected(Feature.CEDD),
                                                        selected(Feature.TAMURA),
                                                        notSelected(Feature.FCTH)
                                                ));

        assertThat(selected.size(), is(2));
        assertTrue(selected.contains(Feature.CEDD));
        assertTrue(selected.contains(Feature.TAMURA));
    }

    @Test
    public void shouldCreateBindingInformingIfNoFeatureIsSelected() throws Exception {
        ObservableList<ViewableFeature> features = someFeatures(
                                                        notSelected(Feature.CEDD),
                                                        notSelected(Feature.TAMURA)
                                                        );

        BooleanBinding binding = utils.noFeatureIsSelectedIn(features);

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
