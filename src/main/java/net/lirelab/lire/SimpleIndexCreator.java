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

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class SimpleIndexCreator implements IndexCreator {

    private IndexCreatorCallback callback = new DumbIndexCreatorCallback();
    private List<Feature> features;
    private List<String> paths;
    private String indexDir;
    private LIRE lire;

    public SimpleIndexCreator(LIRE lire,
                              String indexDir,
                              List<Feature> features,
                              List<String> paths) {

        this.lire = lire;
        this.indexDir = indexDir;
        this.features = features;
        this.paths = paths;
    }

    @Override
    public void create() throws IOException {
        GlobalDocumentBuilder docBuilder = lire.createDocumentBuilder();
        addFeaturesToDocumentBuilder(features, docBuilder);
        IndexWriter indexWriter = lire.createIndexWriter(indexDir);

        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);

            callback.beforeAddImageToIndex(i+1, paths.size(), path);

            BufferedImage img = lire.getBufferedImage(path);
            Document document = docBuilder.createDocument(img, path);
            indexWriter.addDocument(document);

            callback.afterAddImageToIndex(i+1, paths.size(), path);
        }

        lire.closeIndexWriter(indexWriter);
        callback.afterIndexAllImages(paths.size());
    }

    private void addFeaturesToDocumentBuilder(List<Feature> features, GlobalDocumentBuilder docBuilder) {
        for (Feature feature : features) {
            docBuilder.addExtractor(feature.getLireClass());
        }
    }

    @Override
    public void setCallback(IndexCreatorCallback callback) {
        this.callback = callback;
    }
}
