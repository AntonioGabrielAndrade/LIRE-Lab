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

    public void checkProgressIndicatorIsNotVisible() throws TimeoutException {
        waitUntilProgressIndicatorIsNotVisible();
    }

    public void waitUntilProgressBarIsVisible() throws TimeoutException {
        waitUntilIsVisible("#status-progress");
    }

    public void waitUntilProgressIndicatorIsVisible() throws TimeoutException {
        waitUntilIsVisible("#status-indicator");
    }

    public void waitUntilProgressBarIsNotVisible() throws TimeoutException {
        waitUntilIsNotVisible("#status-progress");
    }

    public void waitUntilProgressIndicatorIsNotVisible() throws TimeoutException {
        waitUntilIsNotVisible("#status-indicator");
    }

    public void checkComboboxHasFeatures(List<Feature> features) {
        ComboBox<Feature> comboBox = lookup("#features-combo-box").query();

        assertThat(comboBox.getItems(), equalTo(features));
    }

    public void waitUntilStatusMessageIsVisible(String message) throws TimeoutException {
        waitUntilIsVisible(message);
    }

    public void waitUntilStatusMessageIsNotVisible(String message) throws TimeoutException {
        waitUntilIsNotVisible(message);
    }
}
