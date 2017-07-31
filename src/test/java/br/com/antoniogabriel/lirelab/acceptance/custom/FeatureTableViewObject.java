/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        waitUntilIsVisible(feature.getFeatureName(), ".table-view");
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
