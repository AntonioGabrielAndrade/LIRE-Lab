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

package net.lirelab.lire;

import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;

import java.io.IOException;
import java.util.List;

public class ParallelIndexCreator implements IndexCreator {

    private IndexCreatorCallback callback = new DumbIndexCreatorCallback();
    private List<Feature> features;
    private String imagesDir;
    private int numberOfThreads;
    private String indexDir;
    private LIRE lire;

    public ParallelIndexCreator(LIRE lire,
                                String indexDir,
                                List<Feature> features,
                                String imagesDir,
                                int numberOfThreads) {

        this.lire = lire;
        this.indexDir = indexDir;
        this.features = features;
        this.imagesDir = imagesDir;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void create() throws IOException {
        ParallelIndexer indexer = lire.createParallelIndexer(numberOfThreads, indexDir, imagesDir);
        callback.beforeIndexImages();
        addFeaturesToIndexer(features, indexer);

        new Thread(() -> indexer.run()).start();

        while(!indexer.hasEnded()) {
            try {
                Thread.sleep(2000);
                callback.updatePercentage(indexer.getPercentageDone());
            } catch (InterruptedException e) {
                continue;
            }
        }

        callback.updatePercentage(1.0);
    }

    private void addFeaturesToIndexer(List<Feature> features, ParallelIndexer indexer) {
        for (Feature feature : features) {
            indexer.addExtractor(feature.getLireClass());
        }
    }

    @Override
    public void setCallback(IndexCreatorCallback callback) {
        this.callback = callback;
    }
}
