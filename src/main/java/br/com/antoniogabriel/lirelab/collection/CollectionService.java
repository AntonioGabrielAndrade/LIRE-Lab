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

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.lire.QueryRunnerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Singleton
public class CollectionService {

    private PathResolver resolver;
    private CollectionRepository collectionRepository;
    private CollectionsMonitor collectionsMonitor;
    private CreateCollectionRunnerFactory createCollectionRunnerFactory;
    private QueryRunnerFactory queryRunnerFactory;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor,
                             CreateCollectionRunnerFactory createCollectionRunnerFactory,
                             QueryRunnerFactory queryRunnerFactory) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;
        this.createCollectionRunnerFactory = createCollectionRunnerFactory;
        this.queryRunnerFactory = queryRunnerFactory;

        startMonitoringCollectionsInFileSystem();
    }

    private void startMonitoringCollectionsInFileSystem() {
        try {
            collectionsMonitor.startMonitoringCollectionsModificationsInFileSystem();
        } catch (IOException e) {
            throw new LireLabException("Error monitoring collections", e);
        }
    }

    public CreateCollectionRunner getCreateCollectionRunner(CreateCollectionInfo createInfo) {
        CreateCollectionRunner runner = createCollectionRunnerFactory.getCreateRunner(createInfo);
        runner.setOnFinish(() -> collectionsMonitor.executeListeners());
        return runner;
    }

    public List<Collection> getCollections() {
        return collectionRepository.getCollections();
    }

    public void addCollectionsChangeListener(Runnable callback) {
        collectionsMonitor.addListener(callback);
    }

    public List<Image> runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {
        return queryRunnerFactory.createQueryRunner(resolver).runQuery(collection, feature, queryImage);
    }

    public void deleteCollection(Collection collection) {
        deleteCollection(collection.getName());
    }

    public void deleteCollection(String collectionName) {
        collectionRepository.deleteCollection(collectionName);
        collectionsMonitor.executeListeners();
    }

    public Set<String> getCollectionNames() {
        return collectionRepository.getCollectionNames();
    }
}
