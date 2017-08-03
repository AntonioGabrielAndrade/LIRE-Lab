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

package net.lirelab.acceptance.view;

import net.lirelab.acceptance.CollectionTestHelper;
import net.lirelab.app.HomeFXML;
import net.lirelab.collection.*;
import net.lirelab.lire.Feature;
import net.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static net.lirelab.lire.Feature.CEDD;
import static net.lirelab.test_utilities.TestConstants.*;
import static net.lirelab.test_utilities.TestUtils.isHeadless;
import static org.junit.Assert.fail;

public class HomeViewTest extends FXMLTest<HomeFXML> {

    private static final String COLLECTION_NAME_1 = "Collection1";
    private static final String COLLECTION_NAME_2 = "Collection2";

    private static final String NEW_COLLECTION_NAME = "New_Collection";

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
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME_1, TEST_IMAGES, CEDD);
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME_2, TEST_IMAGES, CEDD);

        collection1 = COLLECTION_HELPER.readCollection(COLLECTION_NAME_1);
        collection2 = COLLECTION_HELPER.readCollection(COLLECTION_NAME_2);
    }

    @AfterClass
    public static void deleteCollections() throws Exception {
        COLLECTION_HELPER.deleteCollection(COLLECTION_NAME_1);
        COLLECTION_HELPER.deleteCollection(COLLECTION_NAME_2);
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

        homeView.waitUntilImagesAreListed(IMAGE1 + JPG,
                                                        IMAGE2 + JPG,
                                                        IMAGE3 + JPG,
                                                        IMAGE4 + JPG,
                                                        IMAGE5 + JPG,
                                                        IMAGE6 + JPG,
                                                        IMAGE7 + JPG,
                                                        IMAGE8 + JPG,
                                                        IMAGE9 + JPG,
                                                        IMAGE10 + JPG);
    }

    @Test
    public void shouldUpdateCollectionsListWhenNewCollectionIsCreated() throws Exception {
        try {
            CreateCollectionRunner runner = service.getCreateCollectionRunner(
                                                            new CreateCollectionInfo(
                                                                    NEW_COLLECTION_NAME,
                                                                    TEST_IMAGES,
                                                                    FEATURES,
                                                                    true,
                                                                    100,
                                                                    false,
                                                                    1));

            new Thread(runner).start();

            homeView.waitUntilCollectionIsListed(NEW_COLLECTION_NAME);
        } catch (Exception e) {
            fail();
        } finally {
            COLLECTION_HELPER.deleteCollection(NEW_COLLECTION_NAME);
        }
    }

    @Test
    public void shouldUpdateCollectionsListWhenCollectionIsDeleted() throws Exception {
        try {
            homeView.waitUntilCollectionsAreListed(collection1, collection2);

            COLLECTION_HELPER.deleteCollection(collection1);

            homeView.waitUntilCollectionIsNotListed(collection1);
            homeView.waitUntilCollectionIsListed(collection2);
        } catch (Exception e) {
            fail();
        } finally {
            COLLECTION_HELPER.createRealCollection(COLLECTION_NAME_1, TEST_IMAGES, CEDD);
        }
    }

    @Test
    public void shouldDeleteCollectionByContextMenu() throws Exception {
        // Nasty hack to execute test only if not in headless mode.
        // Apparently Monocle cant show ContextMenu in headless mode.
        if(!isHeadless()) {
            try {
                homeView.waitUntilCollectionsAreListed(collection1, collection2);

                homeView.deleteByContextMenu(collection1).ok();

                homeView.waitUntilCollectionIsNotListed(collection1);
                homeView.waitUntilCollectionIsListed(collection2);
            } catch (Exception e) {
                fail();
            } finally {
                COLLECTION_HELPER.createRealCollection(COLLECTION_NAME_1, TEST_IMAGES, CEDD);
            }
        }
    }

    @Test
    public void shouldShowCollectionImagesWhenCollectionIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.selectCollection(collection1);

        homeView.waitUntilImagesAreVisible(IMAGE1,
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
    public void shouldShowImageWhenImageIsSelected() throws Exception {
        homeView.waitUntilCollectionIsListed(collection1);
        homeView.expandCollection(collection1);
        homeView.selectImage(IMAGE1 + JPG);
        homeView.waitUntilImageIsVisible(IMAGE1);
    }
}
