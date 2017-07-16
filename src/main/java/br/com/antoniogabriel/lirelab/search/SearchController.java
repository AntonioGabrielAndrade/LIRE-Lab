package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Singleton
public class SearchController implements Initializable {

    @FXML private SingleImageGrid queryGrid;
    @FXML private TextField queryPathField;
    @FXML private TextField currentQueryField;
    @FXML private PaginatedCollectionGrid outputGrid;
    @FXML private StatusBar statusBar;
    @FXML private Button runLoadedImage;

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

        showCollectionInOutputGrid(collection);

        bindCurrentQueryFieldToQueryGrid();
        bindQueryGridToQueryExecution(collection);

        setStatusBar(collection, feature);
        setupQueryAutoCompletion(collection);
    }

    public void runQuery(Collection collection, Feature feature, Image queryImage) {
        RunQueryTask queryTask = createQueryTask(collection, feature, queryImage);
        statusBar.bindProgressTo(queryTask, "Running query...");

        queryTask.addValueListener((observable, oldValue, images) -> {
            outputGrid.setCollection(images, new UpdateCurrentQuery());
        });

        new Thread(queryTask).start();
    }

    public void rerunQuery(Collection collection, Feature feature) {
        Image image = queryGrid.getImage();
        runQuery(collection, feature, image);
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
            runQuery(collection, statusBar.getSelectedFeature(), newImage);
        });
    }

    private void showCollectionInOutputGrid(Collection collection) {
        outputGrid.setCollection(collection, new UpdateCurrentQuery());
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
        statusBar.clear();
    }

    private void setStatusBar(Collection collection, Feature feature) {
        statusBar.setFeatures(collection.getFeatures(), feature, selectedFeature -> {
            rerunQuery(collection, selectedFeature);
        });
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
