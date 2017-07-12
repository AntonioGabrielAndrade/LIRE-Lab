package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;

public class CreateCollectionViewTest extends FXMLTest<CreateCollectionFXML> {

    private static final String EMPTY = "";
    private static final String ANY_NAME = "Some Name";
    private static final String ANY_PATH = "/some/directory/with/images";
    private static final Feature[] ANY_FEATURES = {CEDD, TAMURA};

    private CreateCollectionViewObject view;

    @Before
    public void setUp() throws Exception {
        view = new CreateCollectionViewObject();
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
