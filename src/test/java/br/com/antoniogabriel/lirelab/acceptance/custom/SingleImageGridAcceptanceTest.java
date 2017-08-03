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

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.single_image_grid.SingleImageGrid;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.IMAGE1_PATH;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.IMAGE2_PATH;

public class SingleImageGridAcceptanceTest extends ApplicationTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private Image image1 = new Image(IMAGE1_PATH, IMAGE1_PATH);
    private Image image2 = new Image(IMAGE2_PATH, IMAGE2_PATH);

    private SingleImageGrid singleImageGrid = new SingleImageGrid();
    private ImageGridViewObject view = new ImageGridViewObject();

    @Override
    public void start(Stage stage) throws Exception {
        singleImageGrid.setImageHeight(100);
        singleImageGrid.setImage(image1);

        Scene scene = new Scene(singleImageGrid, 200, 200);
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowImage() throws Exception {
        view.checkImageIsVisible("14474347006_99aa0fd981_k");
    }

    @Test
    public void shouldShowOnlyOneImage() throws Exception {
        interact(() -> singleImageGrid.setImage(image2));
        view.waitUntilImageIsVisible("16903390174_1d670a5849_h");

        interact(() -> singleImageGrid.setImage(image1));
        view.waitUntilImageIsVisible("14474347006_99aa0fd981_k");
    }

}