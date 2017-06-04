package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.Feature;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.builders.SimpleDocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CollectionCreator {

    private CreateCollectionCallback callback;
    private IndexWriter indexWriter;
    private DocumentBuilder builder;
    private String imagesDir;
    private ArrayList<Feature> features;
    private String indexDir;

    public void createIndex() throws IOException {
        builder = new SimpleDocumentBuilder();
        IndexWriterConfig conf =
                new IndexWriterConfig(
                        new WhitespaceAnalyzer());
        indexWriter = new IndexWriter(FSDirectory.open(Paths.get(indexDir)), conf);
    }

    public void addImagesToIndex() throws IOException {
        if(builder == null || indexWriter == null)
            throw new RuntimeException("Create index before add images");

        ArrayList<String> images =
                FileUtils.getAllImages(new File(imagesDir), true);

        for (int i = 0; i < images.size(); i++) {
            String imageFilePath = images.get(i);
            callback.beforeAddImageToIndex(i, images.size(), imageFilePath);
            try {
                BufferedImage img = ImageIO.read(
                        new FileInputStream(imageFilePath));
                Document document =
                        builder.createDocument(img, imageFilePath);
                indexWriter.addDocument(document);
                callback.afterAddImageToIndex(i, images.size(), imageFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
