package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.IndexCreatorBuilder;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateCollectionController implements Initializable {

    public static final String CREATING_COLLECTION = "Creating collection...";

    @FXML
    private TextField nameField;
    @FXML
    private TextField imagesDirectoryField;
    @FXML
    private TableView<FeatureModel> featuresTable;
    @FXML
    private TableColumn<FeatureModel, Boolean> selectedCol;
    @FXML
    private TableColumn<FeatureModel, String> nameCol;
    @FXML
    private Button createButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupColumns();
        populateTable();
        bindCreateButtonToMandatoryData();
    }

    private void setupColumns() {
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
    }

    private void populateTable() {
        featuresTable.setItems(getFeatureItems());
    }

    private ObservableList<FeatureModel> getFeatureItems() {
        ObservableList<FeatureModel> items = FXCollections.observableArrayList();
        for (Feature feature : Feature.values()) {
            items.add(new FeatureModel(feature));
        }
        return items;
    }

    private void bindCreateButtonToMandatoryData() {
        createButton.disableProperty().bind(
                nameField.textProperty().isEmpty().or(
                        imagesDirectoryField.textProperty().isEmpty().or(
                                noFeatureSelected()
                        )
                )
        );
    }

    private BooleanBinding noFeatureSelected() {
        BooleanBinding binding = null;
        for (FeatureModel feature : featuresTable.getItems()) {
            if(binding == null) {
                binding = feature.selectedProperty().not();
            } else {
                binding = binding.and(feature.selectedProperty().not());
            }
        }
        return binding;
    }


    @FXML
    private void chooseDir(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select the directory that contains the images");
        Window window = getWindow();
        File dir = chooser.showDialog(window);
        if (dir != null) {
            imagesDirectoryField.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        getWindow().hide();
    }

    @FXML
    private void createCollection(ActionEvent event) {
        IndexCreator creator = new IndexCreatorBuilder()
                .aCreator()
                .indexForFeatures(getSelectedFeatures())
                .readImagesFrom(imagesDirectoryField.getText())
                .openIndexIn(System.getProperty("user.home") +
                        "/lirelab/collections/" + nameField.getText() + "/index")
                .build();

        CreateCollectionTask task = new CreateCollectionTask(creator);
        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(getWindow());
        dialog.show();
        new Thread(task).start();
    }

    private Window getWindow() {
        return featuresTable.getScene().getWindow();
    }

    private ArrayList<Feature> getSelectedFeatures() {
        ObservableList<FeatureModel> items = featuresTable.getItems();
        ArrayList<Feature> features = new ArrayList<>();
        for (FeatureModel item : items) {
            if (item.isSelected()) {
                features.add(item.getFeature());
            }
        }
        return features;
    }


    public class FeatureModel {
        private final SimpleBooleanProperty selected;
        private final SimpleStringProperty name;
        private final Feature feature;

        public FeatureModel(Feature feature) {
            this.selected = new SimpleBooleanProperty(false);
            this.name = new SimpleStringProperty(feature.name());
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

}
