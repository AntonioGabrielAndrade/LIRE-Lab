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
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.custom.paginated_collection_grid.PaginatedCollectionGrid;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;

public class PaginatedCollectionGridAcceptanceTest extends ApplicationTest {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);
    private static final int PAGE_SIZE = 3;

    private static final String COLLECTION_NAME = "Collection";

    private static Collection collection;

    private PaginatedCollectionGrid collectionGrid;
    private PaginatedGridViewObject view = new PaginatedGridViewObject();

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
        collectionGrid = new PaginatedCollectionGrid();
        collectionGrid.setPageSize(PAGE_SIZE);
        collectionGrid.setCollection(collection);

        Scene scene = new Scene(collectionGrid, 900, 200);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowImagesPaginated() throws Exception {
        List<Image> images = collection.getImages();

        view.checkImagesAreVisible(images.get(0), images.get(1), images.get(2));

        view.nextPage();

        view.checkImagesAreVisible(images.get(3), images.get(4), images.get(5));

        view.nextPage();

        view.checkImagesAreVisible(images.get(6), images.get(7), images.get(8));

        view.nextPage();

        view.checkImagesAreVisible(images.get(9));
    }

}
