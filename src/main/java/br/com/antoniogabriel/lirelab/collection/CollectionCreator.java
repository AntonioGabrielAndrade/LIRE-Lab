package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.Feature;
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static net.semanticmetadata.lire.utils.LuceneUtils.AnalyzerType;

public class CollectionCreator {

    private CreateCollectionCallback callback;
    private IndexWriter indexWriter;
    private GlobalDocumentBuilder builder;
    private String imagesDir;
    private ArrayList<Feature> features;
    private String indexDir;

    public void createIndex() throws IOException {
        builder = new GlobalDocumentBuilder(false);
        for (Feature feature : features) {
            builder.addExtractor(feature.getLireClass());
        }
        indexWriter = LuceneUtils.createIndexWriter(indexDir, true, AnalyzerType.WhitespaceAnalyzer);
    }

    public void addImagesToIndex() throws IOException {
        if(builder == null || indexWriter == null)
            throw new RuntimeException("Create index before add images");

        ArrayList<String> images =
                FileUtils.getAllImages(new File(imagesDir), true);

        for (int i = 0; i < images.size(); i++) {
            String imageFilePath = images.get(i);
            callback.beforeAddImageToIndex(i+1, images.size(), imageFilePath);
            try {
                BufferedImage img = ImageIO.read(
                        new FileInputStream(imageFilePath));
                Document document =
                        builder.createDocument(img, imageFilePath);
                indexWriter.addDocument(document);
                callback.afterAddImageToIndex(i+1, images.size(), imageFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LuceneUtils.closeWriter(indexWriter);
        callback.afterIndexAllImages(images.size());
    }

    void setCallback(CreateCollectionCallback callback) {
        this.callback = callback;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }
}
