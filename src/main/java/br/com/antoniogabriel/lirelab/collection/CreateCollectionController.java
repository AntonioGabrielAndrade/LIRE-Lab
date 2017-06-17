package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.FeatureTable;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
import java.util.List;
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
    private FeatureTable featuresTable;
    @FXML
    private Button createButton;

    private DialogProvider dialogProvider;

    @Inject
    public CreateCollectionController(DialogProvider dialogProvider) {
        this.dialogProvider = dialogProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTable();
        setupCreateButton();
    }

    private void populateTable() {
        featuresTable.setItems(getViewableFeatures());
    }

    private ObservableList<ViewableFeature> getViewableFeatures() {
        return FeatureUtils.toViewableFeatures(Feature.values());
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
        return featuresTable.noFeatureSelected();
    }

    @FXML
    void chooseImagesDirectory(ActionEvent event) {
        Window parent = getWindowFrom(event);
        File dir = dialogProvider.chooseImagesDirectory(parent);
        if (dir != null) {
            imagesDirectoryField.setText(dir.getAbsolutePath());
        }
    }


    @FXML
    void close(ActionEvent event) {
        getWindowFrom(event).hide();
    }

    @FXML
    private void createCollection(ActionEvent event) {
        CreateCollectionTask task =
                new CreateCollectionTaskFactory()
                        .createTask(collectionName(),
                                    collectionFeatures(),
                                    collectionDirectory(),
                                    imagesDirectory());

        ProgressDialog dialog = new ProgressDialog(task);
        dialog.initOwner(getWindowFrom(event));
        dialog.showAndStart();
    }

    private Window getWindowFrom(ActionEvent event) {
        return dialogProvider.getWindowFrom(event);
    }

    String imagesDirectory() {
        return imagesDirectoryField.getText();
    }

    private String collectionDirectory() {
        return COLLECTIONS_PATH + "/" + collectionName();
    }

    private List<Feature> collectionFeatures() {
        return featuresTable.getSelectedFeatures();
    }

    private String collectionName() {
        return nameField.getText();
    }
}
