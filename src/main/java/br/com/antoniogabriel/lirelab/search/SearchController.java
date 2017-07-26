package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Window;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.net.URL;
import java.util.*;

@Singleton
public class SearchController implements Initializable {

    @FXML private HBox centerBox;
    @FXML private SingleImageGrid queryGrid;
    @FXML private TextField queryPathField;
    @FXML private TextField currentQueryField;
    @FXML private Button runLoadedImage;

    private List<SearchOutput> outputs = new ArrayList<>();

    private CollectionService service;
    private DialogProvider dialogProvider;
    private FileUtils fileUtils;

    private Map<String, Image> nameToImage = new HashMap<>();

    @Inject
    public SearchController(CollectionService service,
                            DialogProvider dialogProvider,
                            FileUtils fileUtils) {

        this.service = service;
        this.dialogProvider = dialogProvider;
        this.fileUtils = fileUtils;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindQueryPathFieldToRunButton();
    }

    public void startSearchSession(Collection collection, Feature feature) {
        clear();
        mapImageNamesToImages(collection);

        bindCurrentQueryFieldToQueryGrid();
        setupFirstOutput(collection, feature);
    }

    private void setupFirstOutput(Collection collection, Feature feature) {
        SearchOutput searchOutput = createAddableSearchOutput(collection, feature);
        setupSearchMechanism(collection, feature, searchOutput);
    }

    private void setupSecondOutput(Collection collection, Feature feature) {
        SearchOutput searchOutput = createRemovableSearchOutput();
        setupSearchMechanism(collection, feature, searchOutput);
    }

    private SearchOutput createAddableSearchOutput(Collection collection, Feature feature) {
        SearchOutput output = new SearchOutput();
        Button addButton = new Button();
        addButton.setGraphic(new TangoIconWrapper("actions:list-add"));
        addButton.setOnAction(event -> {
            setupSecondOutput(collection, feature);
            output.disableTitleGraphics();
        });
        output.addTitleGraphics(addButton);

        return output;
    }

    private SearchOutput createRemovableSearchOutput() {
        SearchOutput output = new SearchOutput();
        Button removeButton = new Button();
        removeButton.setGraphic(new TangoIconWrapper("actions:list-remove"));
        removeButton.setOnAction(event -> {
            Node removedOutput = centerBox.getChildren().remove(centerBox.getChildren().size()-1);
            outputs.remove(removedOutput);
            queryGrid.removeAllListenersButFirst();
            outputs.get(0).enableTitleGraphics();
        });
        output.addTitleGraphics(removeButton);

        return output;
    }

    private void setupSearchMechanism(Collection collection, Feature feature, SearchOutput searchOutput) {
        showCollectionInOutputGrid(collection, searchOutput);

        bindQueryGridToQueryExecution(collection);

        setStatusBar(collection, feature, searchOutput);
        setupQueryAutoCompletion(collection);

        outputs.add(searchOutput);

        HBox.setHgrow(searchOutput, Priority.ALWAYS);
        centerBox.getChildren().add(searchOutput);
    }

    public void runQuery(Collection collection, SearchOutput output, Image queryImage) {
        Feature feature = output.getSelectedFeature();
        RunQueryTask queryTask = createQueryTask(collection, feature, queryImage);
        output.bindProgressTo(queryTask, "Running query...");

        queryTask.addValueListener((observable, oldValue, images) -> {
            output.setCollection(images, new UpdateCurrentQuery());
        });

        queryTask.runningProperty().addListener((observable, wasRunning, isRunning) -> {
            if(!isRunning && queryTask.isDone() && queryTask.hasElapsedTime()) {
                output.setMessage("Search completed in: " + queryTask.getElapsedTime());
            }
        });

        new Thread(queryTask).start();
    }

    public void rerunQuery(Collection collection, SearchOutput searchOutput) {
        Image image = queryGrid.getImage();
        runQuery(collection, searchOutput, image);
    }

    protected RunQueryTask createQueryTask(Collection collection, Feature feature, Image queryImage) {
        return new RunQueryTask(service, collection, feature, queryImage);
    }

    public void chooseQueryImage(ActionEvent event) {
        Window owner = getWindowFrom(event);
        File dir = dialogProvider.chooseImageFile(owner);
        if (dir != null) {
            queryPathField.setText(dir.getAbsolutePath());
            setLoadedQuery();
        }
    }

    public void setLoadedQuery() {
        Image image = new Image(queryPathField.getText(), "");
        currentQueryField.setText(image.getImageName());
        queryGrid.setImage(image);
    }

    private void bindQueryPathFieldToRunButton() {
        queryPathField.textProperty().addListener((observable, oldPath, newPath) -> {
            if(fileUtils.isImage(newPath)) {
                runLoadedImage.setDisable(false);
            } else {
                runLoadedImage.setDisable(true);
            }
        });
    }

    private void bindQueryGridToQueryExecution(Collection collection) {
        queryGrid.setOnChange(newImage -> {
            for (SearchOutput output : outputs) {
                runQuery(collection, output, newImage);
            }
        });
    }

    private void showCollectionInOutputGrid(Collection collection, SearchOutput searchOutput) {
        searchOutput.setCollection(collection, new UpdateCurrentQuery());
    }

    private void mapImageNamesToImages(Collection collection) {
        for (Image image : collection.getImages()) {
            nameToImage.put(image.getImageName(), image);
        }
    }

    private void bindCurrentQueryFieldToQueryGrid() {
        currentQueryField.textProperty().addListener((observable, oldImageName, newImageName) -> {
            Image image;
            if((image = nameToImage.get(newImageName)) != null) {
                queryGrid.setImage(image);
            }
        });
    }

    private AutoCompletionBinding<Image> setupQueryAutoCompletion(Collection collection) {
        return TextFields.bindAutoCompletion(currentQueryField, collection.getImages());
    }

    private void clear() {
        queryGrid.clear();
        currentQueryField.clear();
        queryPathField.clear();
        nameToImage.clear();
        centerBox.getChildren().clear();
        outputs.clear();
    }

    private void setStatusBar(Collection collection, Feature feature, SearchOutput searchOutput) {
        searchOutput.setFeatures(collection.getFeatures(), feature, selectedFeature -> {
            rerunQuery(collection, searchOutput);
        });
        searchOutput.setMessage("Click an image to query with it");
    }

    private Window getWindowFrom(ActionEvent event) {
        return dialogProvider.getWindowFrom(event);
    }

    private class UpdateCurrentQuery implements ImageClickHandler {
        @Override
        public void handle(Image image, MouseEvent event) {
            currentQueryField.setText(image.getImageName());
        }
    }
}
