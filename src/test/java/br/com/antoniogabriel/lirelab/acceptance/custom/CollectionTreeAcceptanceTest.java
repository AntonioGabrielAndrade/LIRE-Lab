package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.acceptance.ListCollectionViewObject;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.collectiontree.CollectionTree;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test.TestUtils.collection;
import static java.util.Arrays.asList;

public class CollectionTreeAcceptanceTest extends ApplicationTest {

    private static final Collection COLLECTION_1 = collection("Collection1", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_2 = collection("Collection2", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_3 = collection("Collection3", TEST_IMAGES, CEDD);
    private static final Collection COLLECTION_4 = collection("Collection4", TEST_IMAGES, CEDD);

    private ListCollectionViewObject view = new ListCollectionViewObject();

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        CollectionTree collectionTree = new CollectionTree();

        collectionTree.setCollections(
                asList(COLLECTION_1, COLLECTION_2, COLLECTION_3, COLLECTION_4));

        Scene scene = new Scene(collectionTree, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldListCollections() throws Exception {
        view.checkCollectionsAreListed(COLLECTION_1, COLLECTION_2, COLLECTION_3);
    }

    @Test
    public void shouldListImagesInCollection() throws Exception {
        view.expandCollection(COLLECTION_1);

        view.checkImageIsListed("14474347006_99aa0fd981_k.jpg");
        view.checkImageIsListed("16903390174_1d670a5849_h.jpg");
        view.checkImageIsListed("17099294578_0ba4068bad_k.jpg");
        view.checkImageIsListed("17338370170_1e620bfb18_h.jpg");
        view.checkImageIsListed("17525978165_86dc26e8cb_h.jpg");
        view.checkImageIsListed("19774866363_757555901c_k.jpg");
        view.checkImageIsListed("25601366680_b57441bb52_k.jpg");
        view.checkImageIsListed("25601374660_78e6a9bba8_k.jpg");
        view.checkImageIsListed("26487616294_b22b87133e_k.jpg");
        view.checkImageIsListed("26489383923_98d419eb0d_k.jpg");
    }
}
