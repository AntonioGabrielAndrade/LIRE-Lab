package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.dialog_header.DialogHeader;
import br.com.antoniogabriel.lirelab.custom.feature_table.FeatureTable;
import br.com.antoniogabriel.lirelab.custom.progress_dialog.ProgressDialog;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.beans.binding.Bindings.when;

public class CreateCollectionController implements Initializable {

    @FXML private DialogHeader dialogHeader;
    @FXML private TextField nameField;
    @FXML private TextField imagesDirectoryField;
    @FXML private CheckBox scanSubdirectoriesCheckbox;
    @FXML private FeatureTable featuresTable;
    @FXML private CheckBox useParallelIndexer;
    @FXML private Spinner<Integer> numberOfThreads;
    @FXML private HBox numberOfThreadsPane;
    @FXML private Button createButton;

    private DialogProvider dialogProvider;
    private CollectionService service;

    @Inject
    public CreateCollectionController(DialogProvider dialogProvider,
                                      CollectionService service) {

        this.dialogProvider = dialogProvider;
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTable();
        bindCreateButton();
        bindParallelIndexerCheckboxToNumberOfThreadsSpinner();
        bindParallelIndexerCheckboxToScanSubdirectoriesCheckBox();
        bindHintMessagesToFields();
    }

    private void bindHintMessagesToFields() {
        SimpleStringProperty nameHint = new SimpleStringProperty("Enter a collection name.");
        SimpleStringProperty imagesDirectoryHint = new SimpleStringProperty("Enter a directory with images.");
        SimpleStringProperty featuresHint = new SimpleStringProperty("Select at least one Feature for indexing.");
        SimpleStringProperty empty = new SimpleStringProperty("");

        dialogHeader.hintProperty().bind(
                 when(nameIsEmpty()).then(nameHint)
                    .otherwise(when(imagesDirectoryIsEmpty()).then(imagesDirectoryHint)
                    .otherwise(when(noFeatureSelected()).then(featuresHint)
                    .otherwise(empty)))
        );
    }

    @FXML
    private void chooseImagesDirectory(ActionEvent event) {
        Window owner = getWindowFrom(event);
        File dir = dialogProvider.chooseImagesDirectory(owner);
        if (dir != null) {
            imagesDirectoryField.setText(dir.getAbsolutePath());
        }
    }

    @FXML
    private void close(ActionEvent event) {
        getWindowFrom(event).hide();
    }

    @FXML
    private void createCollection(ActionEvent event) {
        CreateCollectionInfo createInfo = new CreateCollectionInfo(collectionName(),
                                                                    imagesDirectory(),
                                                                    collectionFeatures(),
                                                                    scanSubdirectories(),
                                                                    useParallelIndexer(),
                                                                    numberOfThreads());

        CreateCollectionRunner runnable = service.getCreateCollectionRunner(createInfo);
        CreateCollectionTask task = new CreateCollectionTask(runnable);
        ProgressDialog dialog = dialogProvider.getProgressDialog(task, getWindowFrom(event));
        dialog.showAndStart();
    }

    private void populateTable() {
        featuresTable.setFeatures(Feature.values());
    }

    private void bindCreateButton() {
        createIsDisabledWhen(nameIsEmpty().or(imagesDirectoryIsEmpty().or(noFeatureSelected())));
    }

    private void bindParallelIndexerCheckboxToNumberOfThreadsSpinner() {
        numberOfThreadsPane.disableProperty().bind(useParallelIndexer.selectedProperty().not());
    }

    private void bindParallelIndexerCheckboxToScanSubdirectoriesCheckBox() {
        useParallelIndexer.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
            if(isSelected) {
                scanSubdirectoriesCheckbox.setSelected(true);
            }
        });
    }

    private int numberOfThreads() {
        return numberOfThreads.getValue();
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
        return featuresTable.noFeatureSelected();
    }

    private Window getWindowFrom(ActionEvent event) {
        return dialogProvider.getWindowFrom(event);
    }

    private String imagesDirectory() {
        return imagesDirectoryField.getText();
    }

    private boolean scanSubdirectories() {
        return scanSubdirectoriesCheckbox.isSelected();
    }

    private boolean useParallelIndexer() {
        return useParallelIndexer.isSelected();
    }

    private List<Feature> collectionFeatures() {
        return Collections.unmodifiableList(featuresTable.getSelectedFeatures());
    }

    private String collectionName() {
        return nameField.getText();
    }
}
