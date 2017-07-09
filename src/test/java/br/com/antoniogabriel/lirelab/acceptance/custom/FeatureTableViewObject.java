package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.scene.control.CheckBox;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;
import static org.junit.Assert.assertTrue;

public class FeatureTableViewObject extends FxRobot {

    public void waitUntilFeaturesAreVisible(Feature... features) throws TimeoutException {
        for (Feature feature : features) {
            waitUntilFeatureIsVisible(feature);
        }
    }

    public void waitUntilFeatureIsVisible(Feature feature) throws TimeoutException {
        waitUntilIsVisible(feature.name(), ".table-view");
    }

    public void selectFeatures(Feature... features) {
        for (Feature feature : features) {
            select(feature);
        }
    }

    public void selectAll() {
        CheckBox checkBox = lookup("#select-all").query();
        clickCheckBox(checkBox);
    }

    public void checkFeaturesAreSelected(Feature... features) {
        for (Feature feature : features) {
            assertTrue(checkboxFor(feature).isSelected());
        }
    }

    private void select(Feature feature) {
        CheckBox checkBox = checkboxFor(feature);
        clickCheckBox(checkBox);
    }

    private void clickCheckBox(CheckBox checkBox) {
        boolean oldValue = checkBox.isSelected();

        clickOn(checkBox).interrupt();

        //workaround when sometimes it wont click the checkbox
        if(checkBox.isSelected() == oldValue) {
            checkBox.setSelected(!oldValue);
        }
    }

    private CheckBox checkboxFor(Feature feature) {
        return lookup(".table-view")
                .lookup(".table-row-cell").nth(feature.ordinal())
                .lookup(".table-cell").nth(0)
                .lookup(".check-box").query();
    }

}
