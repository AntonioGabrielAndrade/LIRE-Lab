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

package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionTestHelper;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import br.com.antoniogabriel.lirelab.util.LireLabUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.*;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.deleteWorkDirectory;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryTest {


    private static final PathResolver RESOLVER = new PathResolver(TEST_ROOT);
    private static final CollectionTestHelper COLLECTION_HELPER = new CollectionTestHelper(RESOLVER);
    private static final CollectionUtils COLLECTION_UTILS = new CollectionUtils(RESOLVER);
    private static final FileUtils FILE_UTILS = new FileUtils();
    private static final CollectionAssembler COLLECTION_ASSEMBLER = new CollectionAssembler(RESOLVER, COLLECTION_UTILS);
    private static final LireLabUtils LIRE_LAB_UTILS = new LireLabUtils(RESOLVER, FILE_UTILS);

    private static final String COLLECTION1_NAME = "Collection1";
    private static final String COLLECTION2_NAME = "Collection2";

    private static Collection collection1;
    private static Collection collection2;

    private CollectionRepository repository = new CollectionRepository(LIRE_LAB_UTILS, COLLECTION_ASSEMBLER);

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

        deleteWorkDirectory(RESOLVER);
    }

    @Test
    public void shouldReturnEmptyCollectionListWhenCollectionsDirectoryDontExist() throws Exception {
        deleteWorkDirectory(RESOLVER);

        assertTrue(repository.getCollections().isEmpty());

        createCollections();

        assertFalse(repository.getCollections().isEmpty());
    }

    @Test
    public void shouldReturnAllCollectionsFromDisk() throws Exception {
        List<Collection> collections = repository.getCollections();

        assertThat(collections.size(), is(2));
        assertTrue(collections.contains(collection1));
        assertTrue(collections.contains(collection2));
    }

    @Test
    public void shouldReturnSpecificCollectionFromDisk() throws Exception {
        Collection collection = repository.getCollection(COLLECTION1_NAME);

        assertThat(collection.getName(), equalTo(COLLECTION1_NAME));
        assertThat(collection.getImages().size(), is(10));
        assertTrue(collection.getFeatures().contains(CEDD));
    }

    @Test
    public void shouldReturnCollectionsWithImagesInfo() throws Exception {
        List<Collection> collections = repository.getCollections();

        Collection collection = collections.get(0);

        List<Image> images = collection.getImages();
        String thumbnailsDir = RESOLVER.getThumbnailsDirectoryPath(collection.getName()) + "/";

        assertThat(images.size(), is(10));

        images.contains(new Image(IMAGE1_PATH, thumbnailsDir + IMAGE1 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE2_PATH, thumbnailsDir + IMAGE2 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE3_PATH, thumbnailsDir + IMAGE3 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE4_PATH, thumbnailsDir + IMAGE4 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE5_PATH, thumbnailsDir + IMAGE5 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE6_PATH, thumbnailsDir + IMAGE6 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE7_PATH, thumbnailsDir + IMAGE7 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE8_PATH, thumbnailsDir + IMAGE8 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE9_PATH, thumbnailsDir + IMAGE9 + ".thumbnail.jpg"));
        images.contains(new Image(IMAGE10_PATH, thumbnailsDir + IMAGE10 + ".thumbnail.jpg"));
    }

    @Test
    public void shouldDeleteCollection() throws Exception {
        String testCollection = "TestCollection";
        COLLECTION_HELPER.createRealCollection(testCollection, TEST_IMAGES, CEDD);
        COLLECTION_HELPER.checkCollectionExists(testCollection);

        repository.deleteCollection(testCollection);

        COLLECTION_HELPER.checkCollectionDontExists(testCollection);
    }

    @Test
    public void shouldReturnCollectionNames() throws Exception {
        Set<String> names = repository.getCollectionNames();

        assertThat(names.size(), is(2));
        assertTrue(names.contains(COLLECTION1_NAME));
        assertTrue(names.contains(COLLECTION2_NAME));
    }
}
