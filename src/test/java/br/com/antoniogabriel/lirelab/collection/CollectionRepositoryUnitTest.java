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

import br.com.antoniogabriel.lirelab.util.LireLabUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CollectionRepositoryUnitTest {

    @Mock private Subfolders subfolders;
    @Mock private Iterator<Path> subfoldersIterator;
    @Mock private Path collectionPath1;
    @Mock private Path collectionPath2;
    @Mock private CollectionAssembler collectionAssembler;
    @Mock private LireLabUtils lireLabUtils;

    private Collection collection1 = new Collection("Collection1");
    private Collection collection2 = new Collection("Collection2");

    private CollectionRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new TestableCollectionRepository();
    }

    @Test
    public void shouldReturnEmptyCollectionListWhenCollectionDirectoryDontExist() throws Exception {
        given(lireLabUtils.collectionsDirectoryExist()).willReturn(false);

        List<Collection> collections = repository.getCollections();

        assertTrue(collections.isEmpty());
    }

    @Test
    public void shouldReturnCollectionsInCollectionsDirectory() throws Exception {
        given(lireLabUtils.collectionsDirectoryExist()).willReturn(true);

        given(subfolders.iterator()).willReturn(subfoldersIterator);

        given(subfoldersIterator.hasNext()).willReturn(true, true, false);
        given(subfoldersIterator.next()).willReturn(collectionPath1, collectionPath2);

        given(lireLabUtils.isCollection(collectionPath1)).willReturn(true);
        given(lireLabUtils.isCollection(collectionPath2)).willReturn(true);

        given(collectionAssembler.assembleCollectionFrom(collectionPath1)).willReturn(collection1);
        given(collectionAssembler.assembleCollectionFrom(collectionPath2)).willReturn(collection2);

        List<Collection> collections = repository.getCollections();

        assertTrue(collections.contains(collection1));
        assertTrue(collections.contains(collection2));
    }

    private class TestableCollectionRepository extends CollectionRepository {

        public TestableCollectionRepository() {
            super(lireLabUtils, collectionAssembler);
        }

        @Override
        protected Subfolders getSubfoldersOf(Path dir) throws IOException {
            return subfolders;
        }
    }
}
