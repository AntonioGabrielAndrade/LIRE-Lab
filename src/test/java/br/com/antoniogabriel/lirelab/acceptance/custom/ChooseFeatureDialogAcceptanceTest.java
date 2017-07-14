package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.ChooseFeatureDialog;
import br.com.antoniogabriel.lirelab.lire.Feature;
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

public class ChooseFeatureDialogAcceptanceTest extends ApplicationTest {

    private ChooseFeatureDialog dialog;
    private ChooseFeatureDialogViewObject view = new ChooseFeatureDialogViewObject();

    private List<Feature> features;
    private FeatureHolder featureHolder = new FeatureHolder();

    @Override
    public void start(Stage stage) throws Exception {}

    @Before
    public void setUp() throws Exception {
        interact(() -> {
            features = asList(CEDD, TAMURA, FCTH, COLOR_HISTOGRAM);
            dialog = new ChooseFeatureDialog(features);
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
        view.waitUntilFeatureIsVisible(TAMURA);
        view.selectFeature(TAMURA);
        view.ok();
        waitUntil(() -> featureHolder.getFeature().equals(TAMURA));
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
