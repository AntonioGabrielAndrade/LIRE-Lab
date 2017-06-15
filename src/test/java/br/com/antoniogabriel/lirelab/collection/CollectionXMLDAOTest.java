package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionXMLDAOTest {

    private static final File TARGET_DIR = new File("src/test/resources/");
    private static final String COLLECTION_XML = "collection.xml";

    private static final String ANY_DIR = "any/dir";
    private static final String ANY_NAME = "My Collection";
    private static final List<Feature> ANY_FEATURES = Arrays.asList(CEDD, TAMURA);

    private CollectionXMLDAO xmlDAO;

    @Before
    public void setUp() throws Exception {
        xmlDAO = new CollectionXMLDAO(TARGET_DIR);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(filePath());
    }

    @Test
    public void shouldCreateCollectionXMLFile() throws Exception {
        Collection collection = new Collection();

        collection.setName(ANY_NAME);
        collection.setImagesDirectory(ANY_DIR);
        collection.setFeatures(ANY_FEATURES);

        xmlDAO.create(collection);

        assertTrue(filesExists());
        assertThat(fileContent(), is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                            "<collection>\n" +
                                            "    <name>My Collection</name>\n" +
                                            "    <imagesDirectory>any/dir</imagesDirectory>\n" +
                                            "    <features>\n" +
                                            "        <feature>CEDD</feature>\n" +
                                            "        <feature>TAMURA</feature>\n" +
                                            "    </features>\n" +
                                            "</collection>\n"));

    }

    private String fileContent() throws IOException {
        return new String(Files.readAllBytes(filePath()));
    }

    private boolean filesExists() {
        return Files.exists(filePath());
    }

    @NotNull
    private Path filePath() {
        return new File(TARGET_DIR, COLLECTION_XML).toPath();
    }
}