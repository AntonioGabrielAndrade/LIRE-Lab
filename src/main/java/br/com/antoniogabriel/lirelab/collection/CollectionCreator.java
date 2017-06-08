package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.main.Feature;
import org.apache.lucene.document.Document;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CollectionCreator {

    private CreateCollectionCallback callback;
    private String imagesDir;
    private List<Feature> features;
    private String indexDir;
    private IndexBuilder indexBuilder;

    public CollectionCreator(IndexBuilder indexBuilder) {
        this.indexBuilder = indexBuilder;
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

    void setCallback(CreateCollectionCallback callback) {
        this.callback = callback;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }

}
