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

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XMLCreatorTest {

    private static final String COLLECTION_NAME = "A Collection";
    private static final String IMAGES_DIR = "/any/dir";
    private static final List<Feature> FEATURES = Arrays.asList(CEDD, TAMURA);

    @Mock private CollectionXMLDAO xmlDAO;
    @Mock private XMLCreatorCallback callback;

    private XMLCreator creator;
    private Collection collection;

    @Before
    public void setUp() throws Exception {
        creator = new XMLCreator(COLLECTION_NAME, IMAGES_DIR, FEATURES, xmlDAO);
        creator.setCallback(callback);

        collection = new CollectionBuilder()
                            .aCollection()
                            .withName(COLLECTION_NAME)
                            .withFeatures(FEATURES)
                            .withImagesDirectory(IMAGES_DIR)
                            .build();
    }

    @Test
    public void shouldCreateCollectionXML() throws Exception {
        creator.create();

        verify(callback).beforeCreateXML();
        verify(xmlDAO).create(collection);
        verify(callback).afterCreateXML();
    }

    private class CollectionBuilder {
        private String collectionName;
        private List<Feature> features;
        private String imagesDir;

        public CollectionBuilder aCollection() {
            return new CollectionBuilder();
        }

        public CollectionBuilder withName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public CollectionBuilder withFeatures(List<Feature> features) {
            this.features = features;
            return this;
        }

        public CollectionBuilder withImagesDirectory(String imagesDir) {
            this.imagesDir = imagesDir;
            return this;
        }

        public Collection build() {
            Collection collection = new Collection();
            collection.setName(collectionName);
            collection.setFeatures(features);
            collection.setImagesDirectory(imagesDir);
            return collection;
        }
    }
}
