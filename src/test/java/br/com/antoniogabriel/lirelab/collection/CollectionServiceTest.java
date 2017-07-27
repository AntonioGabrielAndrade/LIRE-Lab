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
import br.com.antoniogabriel.lirelab.lire.QueryRunner;
import br.com.antoniogabriel.lirelab.lire.QueryRunnerFactory;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionServiceTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private CollectionRepository collectionRepository;
    @Mock private CollectionsMonitor collectionsMonitor;
    @Mock private CreateCollectionRunnerFactory createCollectionRunnerFactory;
    @Mock private QueryRunnerFactory queryRunnerFactory;
    @Mock private PathResolver resolver;
    @Mock private QueryRunner queryRunner;

    private Runnable SOME_CALLBACK = () -> {};

    private CollectionService service;

    @Before
    public void  setUp() throws Exception {
        resolver = new PathResolver();
        service = new CollectionService(resolver,
                                        collectionRepository,
                                        collectionsMonitor,
                                        createCollectionRunnerFactory,
                                        queryRunnerFactory);
    }

    @Test
    public void shouldStartMonitoringCollections() throws Exception {
        verify(collectionsMonitor).startMonitoringCollectionsModificationsInFileSystem();
    }

    @Test
    public void shouldGetCollections() throws Exception {
        service.getCollections();

        verify(collectionRepository).getCollections();
    }

    @Test
    public void shouldGetCollectionNames() throws Exception {
        service.getCollectionNames();

        verify(collectionRepository).getCollectionNames();
    }

    @Test
    public void shouldDeleteCollection() throws Exception {
        Collection collection = new Collection("collection");
        service.deleteCollection(collection);

        verify(collectionRepository).deleteCollection("collection");
    }

    @Test
    public void shouldRegisterListenersForCollectionsChanges() throws Exception {
        service.addCollectionsChangeListener(SOME_CALLBACK);

        verify(collectionsMonitor).addListener(SOME_CALLBACK);
    }

    @Test
    public void shouldRunQuery() throws Exception {
        Collection collection = new Collection();
        Feature feature = Feature.CEDD;
        Image queryImage = new Image("", "");

        given(queryRunnerFactory.createQueryRunner(resolver))
                .willReturn(queryRunner);

        service.runQuery(collection, feature, queryImage);

        verify(queryRunner).runQuery(collection, feature, queryImage);
    }
}
