package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class ChooseFeatureDialogViewObject extends FxRobot {

    public void selectFeature(Feature feature) throws TimeoutException {
        clickOn(feature.getFeatureName()).interrupt();
    }

    public void ok() {
        clickOn("OK");
    }

    public void cancel() throws TimeoutException {
        waitUntilIsVisible("Cancel");
        clickOn("Cancel");
    }

    public void checkOptionsAreAvailable(Feature... features) throws TimeoutException {
        for (Feature feature : features) {
            waitUntilIsVisible(feature.getFeatureName());
        }
    }
}
