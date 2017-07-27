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

package br.com.antoniogabriel.lirelab.custom.statusbar;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.beans.binding.Bindings.when;

public class StatusBar extends BorderPane {

    private static final String STATUS_BAR_FXML = "status-bar.fxml";

    @FXML private Label statusMessage;
    @FXML private ProgressBar statusProgress;
    @FXML private ProgressIndicator statusIndicator;
    @FXML private ComboBox<Feature> featuresComboBox;

    private List<ComboBoxListener> comboBoxListeners = new ArrayList<>();

    public StatusBar() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(STATUS_BAR_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void bindProgressTo(Task<?> task) {
        statusProgress.visibleProperty().bind(task.runningProperty());
    }

    public void bindProgressTo(Task<?> task, String message) {
        SimpleStringProperty messageProperty = new SimpleStringProperty(message);
        SimpleStringProperty empty = new SimpleStringProperty("");

        statusProgress.visibleProperty().bind(task.runningProperty());
        statusIndicator.visibleProperty().bind(task.runningProperty());
        statusMessage.textProperty().bind(when(statusIndicator.visibleProperty())
                                            .then(messageProperty).otherwise(empty));
    }

    public void setMessage(String message) {
        statusMessage.textProperty().unbind();
        statusMessage.setText(message);
        statusMessage.setTooltip(new Tooltip(message));
    }

    public void setFeatures(List<Feature> features, Feature defaultFeature, FeatureSelectionListener listener) {
        featuresComboBox.setItems(FXCollections.observableList(features));
        selectFeature(defaultFeature);

        ComboBoxListener comboBoxListener = new ComboBoxListener(listener);
        comboBoxListeners.add(comboBoxListener);

        featuresComboBox.valueProperty().addListener(comboBoxListener);
    }

    public void clear() {
        for (ComboBoxListener comboBoxListener : comboBoxListeners) {
            featuresComboBox.valueProperty().removeListener(comboBoxListener);
        }
        comboBoxListeners.clear();
    }

    public void selectFeature(Feature feature) {
        featuresComboBox.valueProperty().setValue(feature);
    }

    public Feature getSelectedFeature() {
        return featuresComboBox.getValue();
    }

    private class ComboBoxListener implements ChangeListener<Feature> {
        private FeatureSelectionListener listener;

        public ComboBoxListener(FeatureSelectionListener listener) {
            this.listener = listener;
        }

        @Override
        public void changed(ObservableValue<? extends Feature> observable, Feature oldValue, Feature newValue) {
            listener.selected(newValue);
        }
    }
}
