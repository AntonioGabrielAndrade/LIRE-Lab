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
