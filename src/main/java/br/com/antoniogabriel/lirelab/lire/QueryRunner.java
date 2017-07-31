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

package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QueryRunner {

    private PathResolver resolver;
    private LIRE lire;
    private CollectionUtils collectionUtils;

    public QueryRunner(PathResolver resolver, LIRE lire, CollectionUtils collectionUtils) {
        this.resolver = resolver;
        this.lire = lire;
        this.collectionUtils = collectionUtils;
    }

    public List<Image> runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {
        String queryPath  = queryImage.getImagePath();
        String indexDir = resolver.getIndexDirectoryPath(collection.getName());
        int maxHits = collection.totalImages();
        Class<? extends GlobalFeature> globalFeature = feature.getLireClass();

        ImagesSearchedCallback callback = createSearcherCallback(collection);
        LireIndexSearcher searcher = createIndexSearcher(callback);
        searcher.search(queryPath, indexDir, queryImage.getDocId(), globalFeature, maxHits);

        return Collections.unmodifiableList(callback.getImages());
    }

    protected LireIndexSearcher createIndexSearcher(ImagesSearchedCallback callback) {
        return new LireIndexSearcher(lire, callback);
    }

    protected ImagesSearchedCallback createSearcherCallback(Collection collection) {
        return new ImagesSearchedCallback(collectionUtils, collection);
    }

}
