package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CollectionService {

    private PathResolver resolver;
    private CollectionRepository collectionRepository;
    private CollectionsMonitor collectionsMonitor;

    @Inject
    public CollectionService(PathResolver resolver,
                             CollectionRepository collectionRepository,
                             CollectionsMonitor collectionsMonitor) {

        this.resolver = resolver;
        this.collectionRepository = collectionRepository;
        this.collectionsMonitor = collectionsMonitor;

        startMonitoringCollectionsDeleteAndUpdate();
    }

    private void startMonitoringCollectionsDeleteAndUpdate() {
        try {
            this.collectionsMonitor.startMonitoringCollectionsDeleteAndUpdate();
        } catch (IOException e) {
            throw new LireLabException("Error monitoring collections", e);
        }
    }


    public CreateCollectionTask getTaskToCreateCollection(String collectionName,
                                                          String imagesPath,
                                                          List<Feature> collectionFeatures) {

        CreateCollectionTask task =
                new CreateCollectionTaskFactory()
                                .createTask(
                                        collectionName,
                                        collectionFeatures,
                                        imagesPath,
                                        resolver.getCollectionPath(collectionName),
                                        resolver.getIndexDirectoryPath(collectionName),
                                        resolver.getThumbnailsDirectoryPath(collectionName)
                                );

        collectionsMonitor.bindListenersTo(task);
        return task;

    }

    public List<Collection> getCollections() {
        return collectionRepository.getCollections();
    }

    public void addCollectionsChangeListener(Runnable callback) {
        collectionsMonitor.addListener(callback);
    }

    public Collection runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {
        BufferedImage img = null;
        boolean passed = false;
        File f = new File(queryImage.getImagePath());
        if (f.exists()) {
            try {
                img = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        IndexReader ir = DirectoryReader.open(FSDirectory.open(Paths.get(resolver.getIndexDirectoryPath(collection.getName()))));
        ImageSearcher searcher = new GenericFastImageSearcher(collection.getImages().size(), feature.getLireClass());

        List<Image> result = new ArrayList<>();
        CollectionUtils utils = new CollectionUtils(resolver);

        // searching with a image file ...
        ImageSearchHits hits = searcher.search(img, ir);
        // searching with a Lucene document instance ...
        for (int i = 0; i < hits.length(); i++) {
            String fileName = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            Image image = new Image(fileName, utils.getThumbnailPathFromImagePath(collection, fileName));
            result.add(image);
            System.out.println(hits.score(i) + ": \t" + fileName);
        }

        collection.setImages(result);
        return collection;
    }
}
