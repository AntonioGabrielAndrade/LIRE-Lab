package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ChooseFeatureDialogViewObject extends FxRobot {

    public void selectFeature(Feature feature) {
        openPopup();
        clickOn(feature.name()).interrupt();
    }

    private void openPopup() {
        clickOn(".list-cell").interrupt();
    }

    public void ok() {
        clickOn("OK");
    }

    public void cancel() {
        clickOn("Cancel");
    }

    public void checkOptionsAreAvailable(Feature... features) {
        openPopup();
        for (Feature feature : features) {
            verifyThat(feature.name(), isVisible());
        }
    }
}
