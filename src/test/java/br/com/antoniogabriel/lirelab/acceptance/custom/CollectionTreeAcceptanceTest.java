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

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collection_tree.CollectionTree;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.*;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.collection;
import static java.util.Arrays.asList;

public class CollectionTreeAcceptanceTest extends ApplicationTest {

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_3 = collection("Collection3", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_4 = collection("Collection4", TEST_IMAGES, CEDD);

    private CollectionTreeViewObject view = new CollectionTreeViewObject();

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        CollectionTree collectionTree = new CollectionTree();

        collectionTree.setCollections(
                asList(COLLECTION_1, COLLECTION_2, COLLECTION_3, COLLECTION_4));

        Scene scene = new Scene(collectionTree, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldListCollections() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        view.expandCollection(COLLECTION_1);

        view.waitUntilImageIsListed(IMAGE1 + JPG);
        view.waitUntilImageIsListed(IMAGE2 + JPG);
        view.waitUntilImageIsListed(IMAGE3 + JPG);
        view.waitUntilImageIsListed(IMAGE4 + JPG);
        view.waitUntilImageIsListed(IMAGE5 + JPG);
        view.waitUntilImageIsListed(IMAGE6 + JPG);
        view.waitUntilImageIsListed(IMAGE7 + JPG);
        view.waitUntilImageIsListed(IMAGE8 + JPG);
        view.waitUntilImageIsListed(IMAGE9 + JPG);
        view.waitUntilImageIsListed(IMAGE10 + JPG);
    }
}
