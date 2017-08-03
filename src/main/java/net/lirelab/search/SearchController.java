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

package net.lirelab.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import net.lirelab.collection.Collection;
import net.lirelab.collection.CollectionService;
import net.lirelab.collection.DialogProvider;
import net.lirelab.collection.Image;
import net.lirelab.custom.TangoIconWrapper;
import net.lirelab.custom.collection_grid.ImageClickHandler;
import net.lirelab.custom.single_image_grid.SingleImageGrid;
import net.lirelab.lire.Feature;
import net.lirelab.util.FileUtils;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.net.URL;
import java.util.*;

import static javafx.scene.layout.Priority.ALWAYS;

@Singleton
public class SearchController implements Initializable {

    @FXML private HBox centerBox;
    @FXML private SingleImageGrid queryGrid;
    @FXML private TextField queryPathField;
    @FXML private TextField queryField;
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

        bindQueryFieldToQueryImage();
        bindQueryImageToQueryExecution(collection);

        setupQueryAutoCompletion(collection);

        setupFirstOutput(collection, feature);
    }

    private void setupFirstOutput(Collection collection, Feature feature) {
        SearchOutput searchOutput = createSplittableSearchOutput(collection, feature);
        setupSearchMechanism(collection, feature, searchOutput);
    }

    private void setupSecondOutput(Collection collection, Feature feature) {
        SearchOutput searchOutput = createRemovableSearchOutput();
        setupSearchMechanism(collection, feature, searchOutput);
        if(queryGrid.getImage() != null) {
            runQuery(collection, searchOutput, queryGrid.getImage());
        }
    }

    private SearchOutput createSplittableSearchOutput(Collection collection, Feature feature) {
        SearchOutput output = new SearchOutput();

        Button splitButton = new Button();
        splitButton.setGraphic(new TangoIconWrapper("actions:list-add"));
        splitButton.setTooltip(new Tooltip("split output"));

        splitButton.setOnAction(event -> {
            setupSecondOutput(collection, collection.totalFeatures() > 1 ? collection.getFeature(1) : feature);
            output.disableTitleGraphics();
        });
        output.addTitleGraphics(splitButton);

        return output;
    }

    private SearchOutput createRemovableSearchOutput() {
        SearchOutput output = new SearchOutput();

        Button removeButton = new Button();
        removeButton.setGraphic(new TangoIconWrapper("actions:list-remove"));
        removeButton.setTooltip(new Tooltip("unsplit output"));

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
        showCollectionInOutput(collection, searchOutput);
        setStatusBar(collection, feature, searchOutput);

        outputs.add(searchOutput);
        HBox.setHgrow(searchOutput, ALWAYS);
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
        queryField.setText(image.getImageName());
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

    private void bindQueryImageToQueryExecution(Collection collection) {
        queryGrid.setOnChange(newImage -> {
            for (SearchOutput output : outputs) {
                runQuery(collection, output, newImage);
            }
        });
    }

    private void showCollectionInOutput(Collection collection, SearchOutput searchOutput) {
        searchOutput.setCollection(collection, new UpdateCurrentQuery());
    }

    private void mapImageNamesToImages(Collection collection) {
        for (Image image : collection.getImages()) {
            nameToImage.put(image.getImageName(), image);
        }
    }

    private void bindQueryFieldToQueryImage() {
        queryField.textProperty().addListener((observable, oldImageName, newImageName) -> {
            Image image;
            if((image = nameToImage.get(newImageName)) != null) {
                queryGrid.setImage(image);
            }
        });
    }

    private AutoCompletionBinding<Image> setupQueryAutoCompletion(Collection collection) {
        return TextFields.bindAutoCompletion(queryField, collection.getImages());
    }

    private void clear() {
        queryGrid.clear();
        queryField.clear();
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
            queryField.setText(image.getImageName());
        }
    }
}
