package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.NewChooseFeatureDialog;
import br.com.antoniogabriel.lirelab.lire.Feature;
import com.sun.glass.ui.monocle.MonoclePlatformFactory;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.*;
import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntil;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NewChooseFeatureDialogAcceptanceTest extends ApplicationTest {

    private NewChooseFeatureDialog dialog;
    private NewChooseFeatureDialogViewObject view = new NewChooseFeatureDialogViewObject();

    private List<Feature> features;
    private FeatureHolder featureHolder = new FeatureHolder();

    @Override
    public void start(Stage stage) throws Exception {}

    @Before
    public void setUp() throws Exception {
        Class<MonoclePlatformFactory> clazz = MonoclePlatformFactory.class;
        interact(() -> {
            features = asList(CEDD, TAMURA, FCTH, COLOR_HISTOGRAM);
            dialog = new NewChooseFeatureDialog(features);
        });

        Platform.runLater(() -> featureHolder.setFeature(dialog.showAndGetFeature()));
        waitUntil(() -> dialog.getWindow().isShowing());
        targetWindow(dialog.getWindow());
    }

    @After
    public void tearDown() throws Exception {
        interact(() -> dialog.close());
    }

    @Test
    public void shouldShowOptions() throws Exception {
        view.checkOptionsAreAvailable(CEDD, TAMURA, FCTH, COLOR_HISTOGRAM);
    }

    @Test
    public void shouldSelectFeature() throws Exception {
        view.selectFeature(TAMURA);
        view.ok();

        assertThat(featureHolder.getFeature(), is(TAMURA));
    }

    @Test
    public void shouldCancel() throws Exception {
        view.cancel();
    }

    class FeatureHolder {

        private Feature feature;

        public Feature getFeature() {
            return feature;
        }

        public void setFeature(Feature feature) {
            this.feature = feature;
        }
    }

}
