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
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.search.SearchFXML;
import br.com.antoniogabriel.lirelab.search.SearchController;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.IMAGE1_PATH;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;

public class SearchViewTest extends FXMLTest<SearchFXML> {

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static final String COLLECTION_NAME = "Collection";

    private static Collection collection;

    private SearchController controller;
    private SearchViewObject view = new SearchViewObject();

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(RESOLVER);
            }
        };
    }

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

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        controller = fxml.getController();
    }

    @Test
    public void shouldRunQueryBySelectingQueryFromCollection() throws Exception {
        interact(() -> controller.startSearchSession(collection, CEDD));

        view.waitUntilShowCollection(collection);
        view.selectQuery("14474347006_99aa0fd981_k");
        view.waitUntilShowQuery("14474347006_99aa0fd981_k");

        view.waitUntilImagesAreOrderedLike("14474347006_99aa0fd981_k",
                                            "17338370170_1e620bfb18_h",
                                            "26489383923_98d419eb0d_k",
                                            "25601374660_78e6a9bba8_k",
                                            "17525978165_86dc26e8cb_h",
                                            "26487616294_b22b87133e_k",
                                            "16903390174_1d670a5849_h",
                                            "17099294578_0ba4068bad_k",
                                            "25601366680_b57441bb52_k",
                                            "19774866363_757555901c_k");
    }

    @Test
    public void shouldRunQueryByLoadingQueryFromDisk() throws Exception {
        interact(() -> controller.startSearchSession(collection, CEDD));

        view.waitUntilShowCollection(collection);

        view.setQueryPath(IMAGE1_PATH);
        view.run();

        view.waitUntilShowQuery("14474347006_99aa0fd981_k");

        view.waitUntilImagesAreOrderedLike("14474347006_99aa0fd981_k",
                                            "17338370170_1e620bfb18_h",
                                            "26489383923_98d419eb0d_k",
                                            "25601374660_78e6a9bba8_k",
                                            "17525978165_86dc26e8cb_h",
                                            "26487616294_b22b87133e_k",
                                            "16903390174_1d670a5849_h",
                                            "17099294578_0ba4068bad_k",
                                            "25601366680_b57441bb52_k",
                                            "19774866363_757555901c_k");
    }

    @Test
    public void shouldEnableRunButtonWhenQueryPathIsAValidImage() throws Exception {
        view.setQueryPath(IMAGE1_PATH);
        view.checkRunIsEnabled();
    }

    @Test
    public void shouldDisableRunButtonWhenQueryPathIsNotAValidImage() throws Exception {
        view.setQueryPath(TEST_IMAGES + "invalid-image-name");
        view.checkRunIsDisabled();
    }
}
