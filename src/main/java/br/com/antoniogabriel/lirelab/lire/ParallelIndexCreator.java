package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ParallelIndexCreator implements IndexCreator {

    private IndexCreatorCallback callback = new DumbIndexCreatorCallback();
    private List<Feature> features;
    private String imagesDir;
    private int numberOfThreads;
    private List<String> paths;
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
//        this.paths = paths;
        this.imagesDir = imagesDir;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void create() throws IOException {
//        GlobalDocumentBuilder docBuilder = lire.createDocumentBuilder();
//        addFeaturesToDocumentBuilder(features, docBuilder);
//        IndexWriter indexWriter = lire.createIndexWriter(indexDir);
//
//        for (int i = 0; i < paths.size(); i++) {
//            String path = paths.get(i);
//
//            callback.beforeAddImageToIndex(i+1, paths.size(), path);
//
//            BufferedImage img = lire.getBufferedImage(path);
//            Document document = docBuilder.createDocument(img, path);
//            indexWriter.addDocument(document);
//
//            callback.afterAddImageToIndex(i+1, paths.size(), path);
//        }
//
//        lire.closeIndexWriter(indexWriter);
//        callback.afterIndexAllImages(paths.size());


        // use ParallelIndexer to index all photos from args[0] into "index" ... use 6 threads (actually 7 with the I/O thread).
        ParallelIndexer indexer = new ParallelIndexer(numberOfThreads, indexDir, imagesDir);
        // use this to add you preferred builders. For now we go for CEDD, FCTH and AutoColorCorrelogram

//        indexer.addExtractor(CEDD.class);
//        indexer.addExtractor(FCTH.class);
//        indexer.addExtractor(AutoColorCorrelogram.class);

        callback.beforeIndexImages();
        addFeaturesToIndexer(features, indexer);

        indexer.run();

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

    private class DumbIndexCreatorCallback implements IndexCreatorCallback {
        @Override
        public void beforeIndexImages() {}

        @Override
        public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

        @Override
        public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {}

        @Override
        public void afterIndexAllImages(int totalImages) {}
    }
}
