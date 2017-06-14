package br.com.antoniogabriel.lirelab.lire;

import org.apache.lucene.document.Document;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class IndexCreator {

    private IndexCreatorCallback callback;
    private String imagesDir;
    private List<Feature> features;
    private String indexDir;
    private IndexBuilder indexBuilder;

    public IndexCreator(IndexBuilder indexBuilder,
                        String imagesDir,
                        String indexDir,
                        List<Feature> features) {

        this.indexBuilder = indexBuilder;
        this.imagesDir = imagesDir;
        this.indexDir = indexDir;
        this.features = features;
    }

    public void create() throws IOException {
        indexBuilder.createDocumentBuilder();
        indexBuilder.addFeaturesToDocumentBuilder(features);
        indexBuilder.createIndexWriter(indexDir);
        List<String> paths = indexBuilder.getAllImagesPaths(imagesDir);

        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            callback.beforeAddImageToIndex(i+1, paths.size(), path);
            BufferedImage img = indexBuilder.getBufferedImage(path);
            Document document = indexBuilder.createDocument(img, path);
            indexBuilder.addDocument(document);
            callback.afterAddImageToIndex(i+1, paths.size(), path);
        }

        indexBuilder.closeIndexWriter();
        callback.afterIndexAllImages(paths.size());
    }

    public void setCallback(IndexCreatorCallback callback) {
        this.callback = callback;
    }

}
