package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CollectionXMLDAOTest {

    private static final String TARGET_DIR = "src/test/resources/";
    private static final String XML_FILENAME = "collection.xml";
    private static final Path XML_PATH = Paths.get(TARGET_DIR, XML_FILENAME);

    private static final String COLLECTION_NAME = "Test Collection";
    private static final String IMAGES_DIRECTORY = "/some/example/path";
    private static final List<Feature> FEATURES = Arrays.asList(CEDD, TAMURA);

    private static final String XML_CONTENT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<collection>\n" +
            "    <name>" + COLLECTION_NAME + "</name>\n" +
            "    <imagesDirectory>" + IMAGES_DIRECTORY + "</imagesDirectory>\n" +
            "    <features>\n" +
            "        <feature>" + FEATURES.get(0).name() + "</feature>\n" +
            "        <feature>" + FEATURES.get(1).name() + "</feature>\n" +
            "    </features>\n" +
            "</collection>\n";

    private CollectionXMLDAO xmlDAO;
    private Collection collection;

    @Before
    public void setUp() throws Exception {
        xmlDAO = new CollectionXMLDAO(TARGET_DIR);
        collection = createTestCollection(COLLECTION_NAME, IMAGES_DIRECTORY, FEATURES);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(XML_PATH);
    }

    @Test
    public void shouldCreateCollectionXMLFile() throws Exception {
        xmlDAO.create(collection);

        assertThat(Files.exists(XML_PATH), is(true));
        assertThat(fileContent(XML_PATH), is(XML_CONTENT));
    }

    @Test
    public void shouldReadCollectionXMLFile() throws Exception {
        Files.write(XML_PATH, XML_CONTENT.getBytes());

        Collection retrievedCollection = xmlDAO.readCollection();

        assertThat(retrievedCollection, equalTo(collection));
    }

    private String fileContent(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private Collection createTestCollection(String name,
                                            String imagesDir,
                                            List<Feature> features) {

        Collection collection = new Collection();

        collection.setName(name);
        collection.setImagesDirectory(imagesDir);
        collection.setFeatures(features);

        return collection;
    }
}