package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;

public class CreateCollectionViewTest extends ApplicationTest {

    private static final String EMPTY = "";
    private static final String ANY_NAME = "Any Name";
    private static final String ANY_PATH = "/any/dir/path";
    private static final Feature[] ANY_FEATURES = {CEDD, TAMURA};

    @Inject private CreateCollectionFXML fxml;

    private CreateCollectionView view;

    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this);
        fxml.showIn(stage);
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
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenImagesDirectoryIsEmpty() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(EMPTY);
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenNoFeatureIsSelected() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(ANY_PATH);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldEnableCreateWhenAllDataIsInformed() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(ANY_PATH);
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsEnabled();
    }

}
