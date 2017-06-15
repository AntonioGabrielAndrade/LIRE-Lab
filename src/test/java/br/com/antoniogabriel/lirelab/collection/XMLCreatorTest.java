package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class XMLCreatorTest {

    private static final String COLLECTION_NAME = "A Collection";
    private static final String IMAGES_DIR = "/any/dir";
    private static final List<Feature> FEATURES = Arrays.asList(CEDD, TAMURA);

    @Mock CollectionXMLDAO xmlDAO;
    @Mock XMLCreatorCallback callback;

    private XMLCreator creator;
    private Collection collection;

    @Before
    public void setUp() throws Exception {
        creator = new XMLCreator(COLLECTION_NAME, IMAGES_DIR, FEATURES, xmlDAO);
        creator.setCallback(callback);

        collection = new CollectionBuilder()
                            .aCollection()
                            .withName(COLLECTION_NAME)
                            .withFeatures(FEATURES)
                            .withImagesDirectory(IMAGES_DIR)
                            .build();
    }

    @Test
    public void shouldCreateCollectionXML() throws Exception {
        creator.create();

        verify(callback).beforeCreateXML();
        verify(xmlDAO).create(collection);
        verify(callback).afterCreateXML();
    }

    private class CollectionBuilder {
        private String collectionName;
        private List<Feature> features;
        private String imagesDir;

        public CollectionBuilder aCollection() {
            return new CollectionBuilder();
        }

        public CollectionBuilder withName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public CollectionBuilder withFeatures(List<Feature> features) {
            this.features = features;
            return this;
        }

        public CollectionBuilder withImagesDirectory(String imagesDir) {
            this.imagesDir = imagesDir;
            return this;
        }

        public Collection build() {
            Collection collection = new Collection();
            collection.setName(collectionName);
            collection.setFeatures(features);
            collection.setImagesDirectory(imagesDir);
            return collection;
        }
    }
}
