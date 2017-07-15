package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.scene.control.ComboBox;
import org.testfx.api.FxRobot;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsNotVisible;
import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class StatusBarViewObject extends FxRobot {

    public void waitUntilStatusMessageIs(String message) throws TimeoutException, InterruptedException {
        waitUntilIsVisible(message, "#status-root");
    }

    public void checkProgressBarIsNotVisible() throws TimeoutException {
        waitUntilProgressBarIsNotVisible();
    }

    public void waitUntilProgressBarIsVisible() throws TimeoutException {
        waitUntilIsVisible("#status-progress");
    }

    public void waitUntilProgressBarIsNotVisible() throws TimeoutException {
        waitUntilIsNotVisible("#status-progress");
    }

    public void checkComboboxHasFeatures(List<Feature> features) {
        ComboBox<Feature> comboBox = lookup("#features-combo-box").query();

        assertThat(comboBox.getItems(), equalTo(features));
    }
}
