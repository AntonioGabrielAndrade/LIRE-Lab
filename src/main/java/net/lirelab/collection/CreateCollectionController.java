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

package net.lirelab.collection;

import net.lirelab.custom.dialog_header.DialogHeader;
import net.lirelab.custom.feature_table.FeatureTable;
import net.lirelab.custom.progress_dialog.ProgressDialog;
import net.lirelab.lire.Feature;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.paint.Paint;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.Collections.unmodifiableList;
import static javafx.beans.binding.Bindings.when;

public class CreateCollectionController implements Initializable {

    public static final SimpleStringProperty NAME_HINT = new SimpleStringProperty("Enter a collection name.");
    public static final SimpleStringProperty IMAGES_DIRECTORY_HINT = new SimpleStringProperty("Enter a directory with images.");
    public static final SimpleStringProperty FEATURES_HINT = new SimpleStringProperty("Select at least one Feature for indexing.");
    public static final SimpleStringProperty NAME_ALREADY_EXISTS_HINT = new SimpleStringProperty("A collection with this name already exists.");
    public static final SimpleStringProperty EMPTY = new SimpleStringProperty("");

    public static final SimpleObjectProperty RED = new SimpleObjectProperty(Paint.valueOf("red"));
    public static final SimpleObjectProperty BLACK = new SimpleObjectProperty(Paint.valueOf("black"));

    @FXML private DialogHeader dialogHeader;
    @FXML private TextField nameField;
    @FXML private TextField imagesDirectoryField;
    @FXML private CheckBox scanSubdirectoriesCheckbox;
    @FXML private Spinner<Integer> thumbnailHeight;
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
        dialogHeader.hintProperty().bind(when(nameIsEmpty()).then(NAME_HINT)
                                        .otherwise(when(nameAlreadyExists()).then(NAME_ALREADY_EXISTS_HINT)
                                        .otherwise(when(imagesDirectoryIsEmpty()).then(IMAGES_DIRECTORY_HINT)
                                        .otherwise(when(noFeatureSelected()).then(FEATURES_HINT)
                                        .otherwise(EMPTY)))));

        dialogHeader.hintColor().bind(when(nameAlreadyExists()).then(RED).otherwise(BLACK));
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
                                                                    thumbnailsHeight(),
                                                                    useParallelIndexer(),
                                                                    numberOfThreads());

        CreateCollectionRunner runner = service.getCreateCollectionRunner(createInfo);
        CreateCollectionTask task = new CreateCollectionTask(runner);

        Window window = getWindowFrom(event);
        ProgressDialog dialog = dialogProvider.getProgressDialog(task, window);
        setupCancelBehaviour(dialog);

        dialog.showAndStart();
    }

    private void setupCancelBehaviour(ProgressDialog dialog) {
        DeleteCollectionTask cancelTask = new DeleteCollectionTask(collectionName(), service);
        dialog.setOnCancel(event -> {
            if(dialogProvider.confirmAbortCreateCollectionTask()) {
                ProgressDialog cancelDialog = dialogProvider.getProgressDialog(cancelTask, dialog.getDialogPane().getScene().getWindow());
                cancelTask.setOnSucceeded(e -> cancelDialog.close());
                cancelDialog.showAndStart();
                dialog.close();
            }
        });
    }

    private int thumbnailsHeight() {
        return thumbnailHeight.getValue();
    }

    private void populateTable() {
        featuresTable.setFeatures(Feature.values());
    }

    private void bindCreateButton() {
        createIsDisabledWhen(nameIsEmpty().or(imagesDirectoryIsEmpty().or(noFeatureSelected().or(nameAlreadyExists()))));
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

    private BooleanBinding nameAlreadyExists() {
        BooleanBinding binding = bindFalse();

        for (String name : service.getCollectionNames()) {
            binding = binding.or(nameField.textProperty().isEqualTo(name));
        }

        return binding;
    }

    private BooleanBinding bindFalse() {
        return new SimpleBooleanProperty(true).not();
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
        return unmodifiableList(featuresTable.getSelectedFeatures());
    }

    private String collectionName() {
        return nameField.getText();
    }
}
