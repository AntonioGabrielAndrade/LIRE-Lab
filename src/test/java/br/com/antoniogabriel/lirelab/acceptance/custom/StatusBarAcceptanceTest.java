package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.statusbar.FeatureSelectionListener;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatusBarAcceptanceTest extends ApplicationTest {

    private StatusBar statusBar;
    private StatusBarViewObject view = new StatusBarViewObject();

    @Override
    public void start(Stage stage) throws Exception {
        statusBar = new StatusBar();
        Scene scene = new Scene(statusBar, 900, 30);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldSetFeatures() throws Exception {
        List<Feature> features = asList(CEDD, TAMURA, COLOR_HISTOGRAM);
        Feature[] featuresArray = new Feature[1];

        FeatureSelectionListener listener = feature -> featuresArray[0] = feature;

        interact(() -> {
            statusBar.setFeatures(features, CEDD, listener);
            statusBar.selectFeature(TAMURA);
        });

        view.checkComboboxHasFeatures(features);
        assertThat(featuresArray[0], is(TAMURA));
    }

    @Test
    public void shouldShowProgressBarWhileGivenTaskRun() throws Exception {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000);
                return null;
            }
        };

        statusBar.bindProgressTo(task);
        view.checkProgressBarIsNotVisible();

        new Thread(task).start();

        view.waitUntilProgressBarIsVisible();
        view.waitUntilProgressBarIsNotVisible();
    }

    @Test
    public void shouldShowProgressIndicatorAndMessageWhileGivenTaskRun() throws Exception {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000);
                return null;
            }
        };

        String message = "Status message...";

        statusBar.bindProgressTo(task, message);
        view.checkProgressIndicatorIsNotVisible();

        new Thread(task).start();

        view.waitUntilProgressIndicatorIsVisible();
        view.waitUntilStatusMessageIsVisible(message);

        view.waitUntilProgressIndicatorIsNotVisible();
        view.waitUntilStatusMessageIsNotVisible(message);
    }
}
