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

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.custom.collection_grid.CollectionGrid;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.deleteWorkDirectory;

public class CollectionGridAcceptanceTest extends ApplicationTest {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static final String COLLECTION_NAME = "Collection";

    private static Collection collection;

    private CollectionGrid collectionGrid;
    private CollectionGridViewObject view = new CollectionGridViewObject();

    @BeforeClass
    public static void createCollections() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME, TEST_IMAGES, CEDD);
        collection = COLLECTION_HELPER.readCollection(COLLECTION_NAME);
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        COLLECTION_HELPER.deleteCollection(COLLECTION_NAME);
        deleteWorkDirectory(RESOLVER);
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        collectionGrid = new CollectionGrid();
        collectionGrid.setCollection(collection);

        Scene scene = new Scene(collectionGrid, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowImages() throws Exception {
        view.checkImagesAreVisible( "14474347006_99aa0fd981_k",
                                        "16903390174_1d670a5849_h",
                                        "17099294578_0ba4068bad_k",
                                        "17338370170_1e620bfb18_h",
                                        "17525978165_86dc26e8cb_h",
                                        "19774866363_757555901c_k",
                                        "25601366680_b57441bb52_k",
                                        "25601374660_78e6a9bba8_k",
                                        "26487616294_b22b87133e_k",
                                        "26489383923_98d419eb0d_k");
    }

    @Test
    public void shouldDisplayImageInDialogWhenClicked() throws Exception {
        ImageDialogViewObject dialogView = view.selectImage("14474347006_99aa0fd981_k");
        dialogView.checkImageIsDisplayed("14474347006_99aa0fd981_k");
    }
}
