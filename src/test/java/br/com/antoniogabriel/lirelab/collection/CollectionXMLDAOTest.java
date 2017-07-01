package br.com.antoniogabriel.lirelab.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test.TestUtils.collection;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionXMLDAOTest {

    private static final String XML_FILENAME = "collection.xml";
    private static final Path XML_PATH = Paths.get(TEST_ROOT, XML_FILENAME);

    private static final String A_NAME = "Test Collection";
    private static final String A_DIRECTORY = "/some/example/path";

    private static final String XML_CONTENT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<collection>\n" +
            "    <name>" + A_NAME + "</name>\n" +
            "    <imagesDirectory>" + A_DIRECTORY + "</imagesDirectory>\n" +
            "    <features>\n" +
            "        <feature>" + CEDD.name() + "</feature>\n" +
            "        <feature>" + TAMURA.name() + "</feature>\n" +
            "    </features>\n" +
            "</collection>\n";

    private CollectionXMLDAO xmlDAO;
    private Collection collection;

    @Before
    public void setUp() throws Exception {
        xmlDAO = new CollectionXMLDAO(TEST_ROOT);
        collection = collection(A_NAME, A_DIRECTORY, CEDD, TAMURA);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(XML_PATH);
    }

    @Test
    public void shouldCreateCollectionXMLFile() throws Exception {
        xmlDAO.create(collection);

        assertTrue(Files.exists(XML_PATH));
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
}