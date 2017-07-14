package br.com.antoniogabriel.lirelab.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {

    private static final String FILE = "file.txt";

    private FileUtils utils;

    @Before
    public void setUp() throws Exception {
        utils = new FileUtils();
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(FILE));
    }

    @Test
    public void shouldAssertThatAFileExists() throws Exception {
        Files.createFile(Paths.get(FILE));

        assertTrue(utils.fileExists(absolutePath(FILE)));
        assertFalse(utils.fileExists("some/inexistent/file"));
    }

    @Test
    public void shouldGetAllImagesPaths() throws Exception {
        List<String> paths = utils.getAllImagesPaths(TEST_IMAGES, false);

        assertThat(paths.size(), is(10));
        assertTrue(paths.contains(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg"));
    }

    @Test
    public void shouldAssertThatAFileIsAnImage() throws Exception {
        Files.createFile(Paths.get(FILE));

        assertTrue(utils.isImage(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg"));
        assertFalse(utils.isImage(absolutePath(FILE)));
    }

    private String absolutePath(String file) {
        return Paths.get(file).toAbsolutePath().toString();
    }
}