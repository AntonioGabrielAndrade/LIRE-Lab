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

    public void viewCollection(String collection) {
        ListCollectionViewObject listView = new ListCollectionViewObject();
        CollectionGridViewObject gridView = new CollectionGridViewObject();

        listView.selectCollection(collection);

        gridView.checkImagesAreVisible("14474347006_99aa0fd981_k.jpg",
                                        "16903390174_1d670a5849_h.jpg",
                                        "17099294578_0ba4068bad_k.jpg",
                                        "17338370170_1e620bfb18_h.jpg",
                                        "17525978165_86dc26e8cb_h.jpg",
                                        "19774866363_757555901c_k.jpg",
                                        "25601366680_b57441bb52_k.jpg",
                                        "25601374660_78e6a9bba8_k.jpg",
                                        "26487616294_b22b87133e_k.jpg",
                                        "26489383923_98d419eb0d_k.jpg");

        ImageDialogViewObject imageView = gridView.selectImage("14474347006_99aa0fd981_k.jpg");

        imageView.checkImageIsDisplayed("14474347006_99aa0fd981_k.jpg");

        imageView.close();
    }
}
