package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.app.WelcomeController.CREATE_COLLECTION;

public class CreateCollectionViewTest extends ApplicationTest {

    private static final Feature[] FEATURES_FOR_TEST = {Feature.CEDD, Feature.TAMURA};
    private static final String EMPTY = "";
    private static final String ANY_PATH = "/any/dir/path";
    private CreateCollectionView view;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMaximized(false);
        Parent root = CreateCollectionFXML.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(CREATE_COLLECTION);
        stage.centerOnScreen();
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
        view = new CreateCollectionView();
    }

    @Test
    public void shouldCloseWhenCancel() throws Exception {
        view.cancel();
        view.checkWindowIsClosed();
    }

    @Test
    public void shouldDisableCreateWhenNameIsEmpty() throws Exception {
        view.unselectAllFeatures();
        view.writeName(EMPTY);
        view.writeImagesDirectory(ANY_PATH);
        view.selectFeatures(FEATURES_FOR_TEST);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenImagesDirectoryIsEmpty() throws Exception {
        view.unselectAllFeatures();
        view.writeName("Some Name");
        view.writeImagesDirectory(EMPTY);
        view.selectFeatures(FEATURES_FOR_TEST);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenNoFeatureIsSelected() throws Exception {
        view.unselectAllFeatures();
        view.writeName("Some Name");
        view.writeImagesDirectory(ANY_PATH);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldEnableCreateWhenAllDataIsInformed() throws Exception {
        view.unselectAllFeatures();
        view.writeName("Some Name");
        view.writeImagesDirectory(ANY_PATH);
        view.selectFeatures(FEATURES_FOR_TEST);
        view.checkCreateIsEnabled();
    }

}
