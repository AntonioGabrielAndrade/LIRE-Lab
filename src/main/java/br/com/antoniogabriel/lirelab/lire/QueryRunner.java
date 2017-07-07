package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryRunner {

    private PathResolver resolver;
    private LIRE lire;
    private CollectionUtils collectionUtils;

    public QueryRunner(PathResolver resolver, LIRE lire, CollectionUtils collectionUtils) {
        this.resolver = resolver;
        this.lire = lire;
        this.collectionUtils = collectionUtils;
    }

    public Collection runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {

        String imagePath  = queryImage.getImagePath();
        String indexDir = resolver.getIndexDirectoryPath(collection.getName());
        int maxHits = collection.totalImages();
        Class<? extends GlobalFeature> globalFeature = feature.getLireClass();

        List<Image> result = new ArrayList<>();

        BufferedImage img = lire.getBufferedImage(imagePath);
        IndexReader ir = lire.createIndexReader(indexDir);
        ImageSearcher searcher = lire.createImageSearcher(maxHits, globalFeature);
        ImageSearchHits hits = searcher.search(img, ir);

        for (int i = 0; i < hits.length(); i++) {
            Document document = ir.document(hits.documentID(i));
            String fileName = document.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            Image image = getImage(collection, fileName);
            result.add(image);
            System.out.println(hits.score(i) + ": \t" + fileName);
        }

        collection.setImages(result);
        return collection;
    }

    private Image getImage(Collection collection, String fileName) {
        return new Image(fileName, collectionUtils.getThumbnailPathFromImagePath(collection, fileName));
    }
}
