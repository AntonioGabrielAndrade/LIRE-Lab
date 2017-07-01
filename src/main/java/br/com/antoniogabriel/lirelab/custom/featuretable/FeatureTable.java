package br.com.antoniogabriel.lirelab.custom.featuretable;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private FeatureUtils featureUtils = new FeatureUtils();

    public FeatureTable() throws IOException {
        loadFXML();
        setupTableColumns();
    }

    protected void loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
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
}
