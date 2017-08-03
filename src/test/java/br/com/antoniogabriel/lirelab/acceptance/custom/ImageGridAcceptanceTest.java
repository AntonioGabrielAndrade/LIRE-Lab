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

import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.semanticmetadata.lire.utils.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.*;

public class ImageGridAcceptanceTest extends ApplicationTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private ImageGrid imageGrid = new ImageGrid();
    private List<String> paths = new ArrayList<>();

    private ImageGridViewObject view = new ImageGridViewObject();

    @Override
    public void start(Stage stage) throws Exception {
        paths = getPaths(TEST_IMAGES);

        imageGrid.setImagesHeight(100);
        imageGrid.setPaths(paths);

        Scene scene = new Scene(imageGrid, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowImages() throws Exception {
        view.checkImagesAreVisible( IMAGE1,
                                    IMAGE2,
                                    IMAGE3,
                                    IMAGE4,
                                    IMAGE5,
                                    IMAGE6,
                                    IMAGE7,
                                    IMAGE8,
                                    IMAGE9,
                                    IMAGE10);
    }

    protected ArrayList<String> getPaths(String testImages) throws IOException {
        return FileUtils.getAllImages(new File(testImages), false);
    }
}
