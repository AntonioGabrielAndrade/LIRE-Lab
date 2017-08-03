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

import net.lirelab.custom.feature_table.FeatureTable;
import net.lirelab.lire.Feature;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class FeatureTableAcceptanceTest extends ApplicationTest {

    private FeatureTable table;

    private FeatureTableViewObject view = new FeatureTableViewObject();
    private Feature[] features = Feature.values();

    @Override
    public void start(Stage stage) throws Exception {
        table = new FeatureTable();
        table.setFeatures(features);
        Scene scene = new Scene(table, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowAllFeatures() throws Exception {
        view.waitUntilFeaturesAreVisible(features);
    }

    @Test
    public void shouldSelectAllFeatures() throws Exception {
        view.selectAll();
        view.checkFeaturesAreSelected(features);
    }
}
