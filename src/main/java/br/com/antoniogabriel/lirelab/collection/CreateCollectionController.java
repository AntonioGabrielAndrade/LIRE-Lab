package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.Feature;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCollectionController implements Initializable {

    @FXML
    private TableView<FeatureModel> featuresTable;
    @FXML
    private TableColumn<FeatureModel, Boolean> selectedCol;
    @FXML
    private TableColumn<FeatureModel, String> nameCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        featuresTable.setItems(getFeatureItems());
    }

    private ObservableList<FeatureModel> getFeatureItems() {
        ObservableList<FeatureModel> items = FXCollections.observableArrayList();
        for (Feature feature : Feature.values()) {
            items.add(new FeatureModel(feature));
        }
        return items;
    }

    public class FeatureModel {
        private final SimpleBooleanProperty selected;
        private final SimpleStringProperty name;

        public FeatureModel(Feature feature) {
            this.selected = new SimpleBooleanProperty(false);
            this.name = new SimpleStringProperty(feature.name());
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
    }

}
