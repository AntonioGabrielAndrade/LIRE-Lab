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

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_IMAGES;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ImageGridAcceptanceTest extends ApplicationTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private ImageGrid imageGrid = new ImageGrid();
    private List<String> paths = new ArrayList<>();

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
        verifyThat("#14474347006_99aa0fd981_k", isVisible());
        verifyThat("#16903390174_1d670a5849_h", isVisible());
        verifyThat("#17099294578_0ba4068bad_k", isVisible());
        verifyThat("#17338370170_1e620bfb18_h", isVisible());
        verifyThat("#17525978165_86dc26e8cb_h", isVisible());
        verifyThat("#19774866363_757555901c_k", isVisible());
        verifyThat("#25601366680_b57441bb52_k", isVisible());
        verifyThat("#25601374660_78e6a9bba8_k", isVisible());
        verifyThat("#26487616294_b22b87133e_k", isVisible());
        verifyThat("#26489383923_98d419eb0d_k", isVisible());
    }

    protected ArrayList<String> getPaths(String testImages) throws IOException {
        return FileUtils.getAllImages(new File(testImages), false);
    }
}
