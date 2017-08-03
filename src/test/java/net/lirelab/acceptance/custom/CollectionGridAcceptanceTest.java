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

import net.lirelab.acceptance.CollectionTestHelper;
import net.lirelab.collection.Collection;
import net.lirelab.collection.PathResolver;
import net.lirelab.custom.collection_grid.CollectionGrid;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.lirelab.lire.Feature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static net.lirelab.test_utilities.TestConstants.*;
import static net.lirelab.test_utilities.TestUtils.deleteWorkDirectory;

public class CollectionGridAcceptanceTest extends ApplicationTest {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static final String COLLECTION_NAME = "Collection";

    private static Collection collection;

    private CollectionGrid collectionGrid;
    private CollectionGridViewObject view = new CollectionGridViewObject();

    @BeforeClass
    public static void createCollections() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME, TEST_IMAGES, Feature.CEDD);
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

    @Test
    public void shouldDisplayImageInDialogWhenClicked() throws Exception {
        ImageDialogViewObject dialogView = view.selectImage(IMAGE1);
        dialogView.checkImageIsDisplayed(IMAGE1);
    }
}
