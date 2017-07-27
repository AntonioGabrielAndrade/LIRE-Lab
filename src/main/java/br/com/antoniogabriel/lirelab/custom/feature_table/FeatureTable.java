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

package br.com.antoniogabriel.lirelab.custom.feature_table;

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;


public class FeatureTable extends TableView<ViewableFeature> {

    public static final String FXML = "feature-table.fxml";

    @FXML private TableColumn<ViewableFeature, Boolean> selectedCol;
    @FXML private TableColumn<ViewableFeature, String> nameCol;

    @FXML private CheckBox selectAll;

    private FeatureUtils featureUtils = new FeatureUtils();

    public FeatureTable() {
        loadFXML();
        setupTableColumns();
        setupSelectAll();
    }

    protected void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new LireLabException("Could not load fxml", e);
        }
    }

    public void setFeatures(Feature... features) {
        setItems(featureUtils.toViewable(features));
    }

    public BooleanBinding noFeatureSelected() {
        return featureUtils.noFeatureIsSelectedIn(getItems());
    }

    public List<Feature> getSelectedFeatures() {
        return featureUtils.getSelectedFeaturesFrom(getItems());
    }

    private void setupTableColumns() {
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
    }

    private void setupSelectAll() {
        selectAll.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
            if(isSelected) {
                selectAllFeatures();
            } else {
                unselectAllFeatures();
            }
        });
    }

    private void selectAllFeatures() {
        for (ViewableFeature viewableFeature : getItems()) {
            viewableFeature.setSelected(true);
        }
    }

    private void unselectAllFeatures() {
        for (ViewableFeature viewableFeature : getItems()) {
            viewableFeature.setSelected(false);
        }
    }
}
