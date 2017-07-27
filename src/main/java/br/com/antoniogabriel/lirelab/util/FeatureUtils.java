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

package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.custom.feature_table.ViewableFeature;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FeatureUtils {

    public ObservableList<ViewableFeature> toViewableFeatures(Feature... features) {
        return Arrays.stream(features)
                .map(f -> new ViewableFeature(f))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public ObservableList<ViewableFeature> toViewable(Feature... features) {
        return this.toViewableFeatures(features);
    }

    public ArrayList<Feature> getSelectedFeaturesFrom(ObservableList<ViewableFeature> viewableFeatures) {
        return viewableFeatures.stream()
                .filter(v -> v.isSelected())
                .map(v -> v.getFeature())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public BooleanBinding noFeatureIsSelectedIn(ObservableList<ViewableFeature> viewableFeatures) {
        return viewableFeatures.stream()
                .map(v -> v.selectedProperty().not())
                .reduce(BooleanBinding::and)
                .get();
    }
}
