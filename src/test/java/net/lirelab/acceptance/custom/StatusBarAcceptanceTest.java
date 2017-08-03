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

package net.lirelab.acceptance.custom;

import net.lirelab.custom.statusbar.FeatureSelectionListener;
import net.lirelab.custom.statusbar.StatusBar;
import net.lirelab.lire.Feature;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

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
        List<Feature> features = asList(Feature.CEDD, Feature.TAMURA, Feature.COLOR_HISTOGRAM);
        Feature[] featuresArray = new Feature[1];

        FeatureSelectionListener listener = feature -> featuresArray[0] = feature;

        interact(() -> {
            statusBar.setFeatures(features, Feature.CEDD, listener);
            statusBar.selectFeature(Feature.TAMURA);
        });

        view.checkComboboxHasFeatures(features);
        assertThat(featuresArray[0], CoreMatchers.is(Feature.TAMURA));
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
