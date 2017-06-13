package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
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

    public static final String HOME_DIRECTORY_PATH = System.getProperty("user.home");
    public static final String LIRELAB_WORK_DIRECTORY = "/lirelab";
    public static final String COLLECTIONS_DIRECTORY = "/collections";
    public static final String COLLECTIONS_PATH = HOME_DIRECTORY_PATH
                                                + LIRELAB_WORK_DIRECTORY
                                                + COLLECTIONS_DIRECTORY;
    @FXML
    private TextField nameField;
    @FXML
    private TextField imagesDirectoryField;
    @FXML
    private TableView<ViewableFeature> featuresTable;
    @FXML
    private TableColumn<ViewableFeature, Boolean> selectedCol;
    @FXML
    private TableColumn<ViewableFeature, String> nameCol;
    @FXML
    private Button createButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        populateTable();
        setupCreateButton();
    }

    private void setupTableColumns() {
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
    }

    private void populateTable() {
        featuresTable.setItems(getViewableFeatures());
    }

    private ObservableList<ViewableFeature> getViewableFeatures() {
        ObservableList<ViewableFeature> items = FXCollections.observableArrayList();
        for (Feature feature : Feature.values()) {
            items.add(new ViewableFeature(feature));
        }
        return items;
    }

    private void setupCreateButton() {
        createIsDisabledWhen(nameIsEmpty().or(imagesDirectoryIsEmpty().or(noFeatureSelected())));
    }

    private void createIsDisabledWhen(ObservableBooleanValue condition) {
        createButton.disableProperty().bind(condition);
    }

    private BooleanBinding nameIsEmpty() {
        return nameField.textProperty().isEmpty();
    }

    private BooleanBinding imagesDirectoryIsEmpty() {
        return imagesDirectoryField.textProperty().isEmpty();
    }

    private BooleanBinding noFeatureSelected() {
        BooleanBinding binding = null;
        for (ViewableFeature feature : featuresTable.getItems()) {
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
        CreateCollectionTask task =
                CreateCollectionTask
                        .aTask()
                        .createCollectionNamed(nameField.getText())
                        .indexedBy(selectedFeatures())
                        .inDirectory(COLLECTIONS_PATH)
                        .readImagesFrom(imagesDirectoryField.getText());

        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(getWindow());
        dialog.showAndStart();
    }

    private Window getWindow() {
        return featuresTable.getScene().getWindow();
    }

    private ArrayList<Feature> selectedFeatures() {
        ObservableList<ViewableFeature> items = featuresTable.getItems();
        ArrayList<Feature> features = new ArrayList<>();
        for (ViewableFeature item : items) {
            if (item.isSelected()) {
                features.add(item.getFeature());
            }
        }
        return features;
    }
}
