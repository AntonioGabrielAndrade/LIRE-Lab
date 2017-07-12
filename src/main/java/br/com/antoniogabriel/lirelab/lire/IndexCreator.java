package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class IndexCreator {

    private IndexCreatorCallback callback = new DumbIndexCreatorCallback();
    private String imagesDir;
    private List<Feature> features;
    private String indexDir;
    private LIRE lire;

    public IndexCreator(LIRE lire,
                        String imagesDir,
                        String indexDir,
                        List<Feature> features) {

        this.lire = lire;
        this.imagesDir = imagesDir;
        this.indexDir = indexDir;
        this.features = features;
    }

    public void create() throws IOException {
        GlobalDocumentBuilder docBuilder = lire.createDocumentBuilder();
        addFeaturesToDocumentBuilder(features, docBuilder);
        IndexWriter indexWriter = lire.createIndexWriter(indexDir);
        List<String> paths = lire.getAllImagesPaths(imagesDir);

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

    public void setCallback(IndexCreatorCallback callback) {
        this.callback = callback;
    }

    private class DumbIndexCreatorCallback implements IndexCreatorCallback {
        @Override
        public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

        @Override
        public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

        @Override
        public void afterIndexAllImages(int totalImages) {}
    }
}
