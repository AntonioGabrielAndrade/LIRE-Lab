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

package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.app.HomeFXML;
import br.com.antoniogabriel.lirelab.collection.*;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.*;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.isHeadless;

public class HomeViewTest extends FXMLTest<HomeFXML> {

    private static final String COLLECTION1_NAME = "Collection1";
    private static final String COLLECTION2_NAME = "Collection2";
    private static final String COLLECTION3_NAME = "Collection3";

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static final List<Feature> FEATURES = Arrays.asList(CEDD);

    private static Collection collection1;
    private static Collection collection2;

    private HomeViewObject homeView = new HomeViewObject();

    @Inject
    private CollectionService service;

    @BeforeClass
    public static void createCollections() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
        COLLECTION_HELPER.createRealCollection(COLLECTION2_NAME, TEST_IMAGES, CEDD);

        collection1 = COLLECTION_HELPER.readCollection(COLLECTION1_NAME);
        collection2 = COLLECTION_HELPER.readCollection(COLLECTION2_NAME);
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        COLLECTION_HELPER.deleteCollection(COLLECTION1_NAME);
        COLLECTION_HELPER.deleteCollection(COLLECTION2_NAME);
        COLLECTION_HELPER.deleteCollection(COLLECTION3_NAME);
    }


    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(RESOLVER);
            }
        };
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Test
    public void shouldListCollections() throws Exception {
        homeView.waitUntilCollectionsAreListed(collection1, collection2);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.expandCollection(collection1);

        homeView.waitUntilImageIsListed(IMAGE1 + JPG);
        homeView.waitUntilImageIsListed(IMAGE2 + JPG);
        homeView.waitUntilImageIsListed(IMAGE3 + JPG);
        homeView.waitUntilImageIsListed(IMAGE4 + JPG);
        homeView.waitUntilImageIsListed(IMAGE5 + JPG);
        homeView.waitUntilImageIsListed(IMAGE6 + JPG);
        homeView.waitUntilImageIsListed(IMAGE7 + JPG);
        homeView.waitUntilImageIsListed(IMAGE8 + JPG);
        homeView.waitUntilImageIsListed(IMAGE9 + JPG);
        homeView.waitUntilImageIsListed(IMAGE10 + JPG);
    }

    @Test
    public void shouldUpdateCollectionsListWhenNewCollectionIsCreated() throws Exception {
        CreateCollectionRunner runner = service.getCreateCollectionRunner(
                                                        new CreateCollectionInfo(
                                                                COLLECTION3_NAME,
                                                                TEST_IMAGES,
                                                                FEATURES,
                                                                true,
                                                                100,
                                                                false,
                                                                1));

        new Thread(runner).start();

        homeView.waitUntilCollectionIsListed(COLLECTION3_NAME);
    }

    @Test
    public void shouldUpdateCollectionsListWhenCollectionIsDeleted() throws Exception {
        homeView.waitUntilCollectionsAreListed(collection1, collection2);

        COLLECTION_HELPER.deleteCollection(collection1);

        homeView.waitUntilCollectionIsNotListed(collection1);
        homeView.waitUntilCollectionsAreListed(collection2);

        COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
    }

    @Test
    public void shouldDeleteCollectionByContextMenu() throws Exception {
        // Nasty hack to execute test only if not in headless mode.
        // Apparently Monocle cant show ContextMenu in headless mode.
        if(!isHeadless()) {
            homeView.waitUntilCollectionsAreListed(collection1, collection2);

            homeView.deleteByContextMenu(collection1).ok();

            homeView.waitUntilCollectionIsNotListed(collection1);
            homeView.waitUntilCollectionsAreListed(collection2);

            COLLECTION_HELPER.createRealCollection(COLLECTION1_NAME, TEST_IMAGES, CEDD);
        }
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.selectCollection(collection1);

        homeView.waitUntilImageIsVisible(IMAGE1);
        homeView.waitUntilImageIsVisible(IMAGE2);
        homeView.waitUntilImageIsVisible(IMAGE3);
        homeView.waitUntilImageIsVisible(IMAGE4);
        homeView.waitUntilImageIsVisible(IMAGE5);
        homeView.waitUntilImageIsVisible(IMAGE6);
        homeView.waitUntilImageIsVisible(IMAGE7);
        homeView.waitUntilImageIsVisible(IMAGE8);
        homeView.waitUntilImageIsVisible(IMAGE9);
        homeView.waitUntilImageIsVisible(IMAGE10);
    }

    @Test
    public void shouldShowImageWhenImageIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.expandCollection(collection1);
        homeView.selectImage(IMAGE1 + JPG);
        homeView.waitUntilImageIsVisible(IMAGE1);
    }
}
