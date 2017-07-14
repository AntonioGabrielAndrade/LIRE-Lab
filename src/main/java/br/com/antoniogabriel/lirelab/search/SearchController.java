package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
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
import javafx.stage.Window;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class SearchController implements Initializable {

    @FXML private SingleImageGrid queryGrid;
    @FXML private TextField queryImageField;
    @FXML private PaginatedCollectionGrid outputGrid;
    @FXML private StatusBar statusBar;
    @FXML private Button runLoadedImage;

    private CollectionService service;
    private DialogProvider dialogProvider;
    private FileUtils fileUtils;

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
        bindQueryPathTextFieldToRunButton();
    }

    private void bindQueryPathTextFieldToRunButton() {
        queryImageField.textProperty().addListener((observable, oldPath, newPath) -> {
            if(fileUtils.isImage(newPath)) {
                runLoadedImage.setDisable(false);
            } else {
                runLoadedImage.setDisable(true);
            }
        });
    }

    public void startSearchSession(Collection collection, Feature feature) {
        queryGrid.clear();
        queryImageField.clear();
        outputGrid.setCollection(collection, new SetImageToGridClickHandler(queryGrid));
        queryGrid.setOnChange(new QueryChangeListener(this, collection, feature));
        setStatusMessage(collection, feature);
    }

    public void runQuery(Collection collection, Feature feature, Image queryImage) {
        RunQueryTask queryTask = createQueryTask(collection, feature, queryImage);
        statusBar.bindProgressTo(queryTask);
        queryTask.addValueListener((observable, oldValue, newValue) -> {
            outputGrid.setCollection(newValue, new SetImageToGridClickHandler(queryGrid));
        });
        new Thread(queryTask).start();
    }

    protected RunQueryTask createQueryTask(Collection collection, Feature feature, Image queryImage) {
        return new RunQueryTask(service, collection, feature, queryImage);
    }

    private void setStatusMessage(Collection collection, Feature feature) {
        statusBar.setSearchStatusInfo(collection, feature);
    }

    public void chooseQueryImage(ActionEvent event) {
        Window owner = getWindowFrom(event);
        File dir = dialogProvider.chooseImageFile(owner);
        if (dir != null) {
            queryImageField.setText(dir.getAbsolutePath());
            setLoadedQuery();
        }
    }

    public void setLoadedQuery() {
        queryGrid.setImage(new Image(queryImageField.getText(), ""));
    }

    private Window getWindowFrom(ActionEvent event) {
        return dialogProvider.getWindowFrom(event);
    }

}
