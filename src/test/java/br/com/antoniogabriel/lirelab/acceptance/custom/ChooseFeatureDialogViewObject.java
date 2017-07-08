package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class ChooseFeatureDialogViewObject extends FxRobot {

    public void selectFeature(Feature feature) throws TimeoutException {
        openPopup();
        clickOn(feature.name()).interrupt();
    }

    private void openPopup() throws TimeoutException {
        //deal with weird testfx exception that cant find scene bounds
        boolean clicked = false;
        while(!clicked) {
            try {
                clickOn(".list-cell").interrupt();
                clicked = true;
            } catch (RuntimeException e) {
                continue;
            }
        }
    }

    public void ok() {
        clickOn("OK");
    }

    public void cancel() {
        clickOn("Cancel");
    }

    public void checkOptionsAreAvailable(Feature... features) throws TimeoutException {
        openPopup();
        for (Feature feature : features) {
            waitUntilIsVisible(feature.name());
        }
    }

    public void waitUntilViewIsVisible() throws TimeoutException {
        waitUntilIsVisible(".list-cell", "#choose-feature-dialog");
    }
}
