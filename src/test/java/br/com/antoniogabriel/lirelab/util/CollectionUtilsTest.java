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

package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.IMAGE1_PATH;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsTest {


    private static final String COLLECTION_NAME = "Collection";

    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final String THUMBNAILS_DIRECTORY_PATH = RESOLVER.getThumbnailsDirectoryPath(COLLECTION_NAME) + "/";
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);

    private static Collection collection;

    private CollectionUtils utils = new CollectionUtils(RESOLVER);

    @BeforeClass
    public static void setUp() throws Exception {
        COLLECTION_HELPER.createRealCollection(COLLECTION_NAME, TEST_IMAGES, CEDD);
        collection = COLLECTION_HELPER.readCollection(COLLECTION_NAME);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        COLLECTION_HELPER.deleteCollection(COLLECTION_NAME);
        deleteWorkDirectory(RESOLVER);
    }

    @Test
    public void shouldGetCollectionThumbnailsPaths() throws Exception {
        List<String> paths = utils.getThumbnailsPaths(collection);

        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "14474347006_99aa0fd981_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "16903390174_1d670a5849_h" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "17099294578_0ba4068bad_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "17338370170_1e620bfb18_h" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "17525978165_86dc26e8cb_h" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "19774866363_757555901c_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "25601366680_b57441bb52_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "25601374660_78e6a9bba8_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "26487616294_b22b87133e_k" + ".thumbnail.jpg"));
        assertTrue(paths.contains(THUMBNAILS_DIRECTORY_PATH + "26489383923_98d419eb0d_k" + ".thumbnail.jpg"));
    }

    @Test
    public void shouldGetThumbnailPathFromImagePath() throws Exception {
        String thumbnailPath = utils.getThumbnailPathFromImagePath(collection, IMAGE1_PATH);

        assertThat(thumbnailPath, equalTo(THUMBNAILS_DIRECTORY_PATH + "14474347006_99aa0fd981_k" + ".thumbnail.jpg"));
    }
}
