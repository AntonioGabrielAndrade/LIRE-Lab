package br.com.antoniogabriel.lirelab.lire;

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
