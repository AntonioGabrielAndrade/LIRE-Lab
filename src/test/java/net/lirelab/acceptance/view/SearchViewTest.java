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

import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import net.lirelab.acceptance.CollectionTestHelper;
import net.lirelab.collection.Collection;
import net.lirelab.collection.PathResolver;
import net.lirelab.search.SearchController;
import net.lirelab.search.SearchFXML;
import net.lirelab.test_utilities.FXMLTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.lirelab.lire.Feature.CEDD;
import static net.lirelab.test_utilities.TestConstants.*;
import static net.lirelab.test_utilities.TestUtils.deleteWorkDirectory;

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
    public static void createCollection() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME, TEST_IMAGES, CEDD);
        collection = COLLECTION_HELPER.readCollection(COLLECTION_NAME);
    }

    @AfterClass
    public static void deleteCollection() throws Exception {
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
        view.selectQuery(IMAGE1);
        view.waitUntilShowQuery(IMAGE1);

        view.waitUntilImagesAreOrderedLike(IMAGE1,
                                            IMAGE4,
                                            IMAGE10,
                                            IMAGE8,
                                            IMAGE5,
                                            IMAGE9,
                                            IMAGE2,
                                            IMAGE3,
                                            IMAGE7,
                                            IMAGE6);
    }

    @Test
    public void shouldRunQueryByLoadingQueryFromDisk() throws Exception {
        interact(() -> controller.startSearchSession(collection, CEDD));

        view.waitUntilShowCollection(collection);

        view.setQueryPath(IMAGE1_PATH);
        view.run();

        view.waitUntilShowQuery(IMAGE1);

        view.waitUntilImagesAreOrderedLike(IMAGE1,
                                            IMAGE4,
                                            IMAGE10,
                                            IMAGE8,
                                            IMAGE5,
                                            IMAGE9,
                                            IMAGE2,
                                            IMAGE3,
                                            IMAGE7,
                                            IMAGE6);
    }

    @Test
    public void shouldEnableRunButtonWhenQueryPathIsAValidImage() throws Exception {
        view.setQueryPath(IMAGE1_PATH);
        view.checkRunIsEnabled();
    }

    @Test
    public void shouldDisableRunButtonWhenQueryPathIsNotAValidImage() throws Exception {
        view.setQueryPath("invalid/image/path");
        view.checkRunIsDisabled();
    }
}
