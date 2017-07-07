package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;

import java.io.IOException;

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
        String queryPath  = queryImage.getImagePath();
        String indexDir = resolver.getIndexDirectoryPath(collection.getName());
        int maxHits = collection.totalImages();
        Class<? extends GlobalFeature> globalFeature = feature.getLireClass();

        ImagesSearchedCallback callback = createSearcherCallback(collection);
        LireIndexSearcher searcher = createIndexSearcher(callback);
        searcher.search(queryPath, indexDir, globalFeature, maxHits);

        collection.setImages(callback.getImages());
        return collection;
    }

    protected LireIndexSearcher createIndexSearcher(ImagesSearchedCallback callback) {
        return new LireIndexSearcher(lire, callback);
    }

    protected ImagesSearchedCallback createSearcherCallback(Collection collection) {
        return new ImagesSearchedCallback(collectionUtils, collection);
    }

}
