package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FeatureTable extends TableView<ViewableFeature> implements Initializable {

    public static final String FXML = "table-view.fxml";

    @FXML
    private TableColumn<ViewableFeature, Boolean> selectedCol;
    @FXML
    private TableColumn<ViewableFeature, String> nameCol;

    public FeatureTable() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
    }

    public BooleanBinding noFeatureSelected() {
        return FeatureUtils.noFeatureIsSelectedIn(getItems());
    }

    public ArrayList<Feature> getSelectedFeatures() {
        return FeatureUtils.getSelectedFeaturesFrom(getItems());
    }

    private void setupTableColumns() {
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
    }
}
