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

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import java.io.IOException;

public class LireIndexSearcher {

    private LIRE lire;
    private IndexSearcherCallback callback;

    public LireIndexSearcher(LIRE lire, IndexSearcherCallback callback) {
        this.lire = lire;
        this.callback = callback;
    }

    public void search(String queryPath,
                       String indexDir,
                       int docId,
                       Class<? extends GlobalFeature> globalFeature,
                       int maxHits) throws IOException {

        IndexReader ir = lire.createIndexReader(indexDir);
        ImageSearcher searcher = lire.createImageSearcher(maxHits, globalFeature);

        ImageSearchHits hits = docId == -1 ?
                                searcher.search(lire.getBufferedImage(queryPath), ir) :
                                searcher.search(ir.document(docId), ir);

        for (int i = 0; i < hits.length(); i++) {
            Document document = ir.document(hits.documentID(i));
            String fileName = document.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            callback.imageSearched(fileName, i, hits.score(i));
        }
    }
}
