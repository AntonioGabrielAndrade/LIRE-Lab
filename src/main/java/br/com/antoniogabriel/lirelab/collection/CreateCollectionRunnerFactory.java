package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.lire.*;
import br.com.antoniogabriel.lirelab.util.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateCollectionRunnerFactory {

    private PathResolver resolver;
    private FileUtils fileUtils;

    @Inject
    public CreateCollectionRunnerFactory(PathResolver resolver, FileUtils fileUtils) {
        this.resolver = resolver;
        this.fileUtils = fileUtils;
    }

    public CreateCollectionRunner getCreateRunner(CreateCollectionInfo createInfo) {
        String collectionPath = resolver.getCollectionPath(createInfo.getCollectionName());
        String indexPath = resolver.getIndexDirectoryPath(createInfo.getCollectionName());
        String thumbnailsPath = resolver.getThumbnailsDirectoryPath(createInfo.getCollectionName());


        LIRE lire = new LIRE();
        List<String> paths;

        try {
            paths = fileUtils.getAllImagesPaths(createInfo.getImagesDirectory(), createInfo.isScanSubdirectories());
        } catch (IOException e) {
            throw new LireLabException("Could not read paths", e);
        }

        IndexCreator indexCreator = getIndexCreator(lire,
                indexPath,
                createInfo.getImagesDirectory(),
                paths,
                createInfo.getFeatures(),
                createInfo.isUseParallelIndexer(),
                createInfo.getNumberOfThreads());

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                thumbnailsPath,
                paths);

        CollectionXMLDAO xmlDAO = new CollectionXMLDAO(new File(collectionPath));
        XMLCreator xmlCreator = new XMLCreator(createInfo.getCollectionName(),
                createInfo.getImagesDirectory(),
                createInfo.getFeatures(),
                xmlDAO);

        return new CreateCollectionRunner(indexCreator, thumbnailsCreator, xmlCreator);
    }

    private IndexCreator getIndexCreator(LIRE lire,
                                         String indexPath,
                                         String imagesPath,
                                         List<String> paths,
                                         List<Feature> collectionFeatures,
                                         boolean useParallelIndexer,
                                         int numberOfThreads) {

        if(useParallelIndexer) {
            return new ParallelIndexCreator(lire,
                                            indexPath,
                                            collectionFeatures,
                                            imagesPath,
                                            numberOfThreads);
        } else {
            return new SimpleIndexCreator(lire,
                                            indexPath,
                                            collectionFeatures,
                                            paths);
        }
    }

}
