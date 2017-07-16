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
