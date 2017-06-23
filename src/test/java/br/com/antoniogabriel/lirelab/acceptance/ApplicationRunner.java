package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.inject.Inject;
import java.util.concurrent.TimeoutException;


public class ApplicationRunner {

    @Inject
    private CollectionHelper collectionHelper;

    public void createCollection(String collectionName,
                                 String imagesDirectory,
                                 Feature... features) throws TimeoutException {

        AppViewObject appView = new AppViewObject();
        CreateCollectionViewObject createView = appView.createCollection();

        createView
                .writeName(collectionName)
                .writeImagesDirectory(imagesDirectory)
                .selectFeatures(features);

        ProgressDialogViewObject progressView = createView.create();

        progressView
                //indexing images
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                //creating thumbnails
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                .checkOkIsEnabledWhenFinish()
                .ok();

        collectionHelper.checkCollectionExists(collectionName);

        appView.checkCollectionIsListed(collectionName);
    }
}
