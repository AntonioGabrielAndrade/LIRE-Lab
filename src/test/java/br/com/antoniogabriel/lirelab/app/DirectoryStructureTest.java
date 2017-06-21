package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.collection.CollectionRepositoryTest.TEST_ROOT;
import static org.junit.Assert.assertTrue;

public class DirectoryStructureTest {

    private PathResolver resolver = new PathResolver(TEST_ROOT);
    private DirectoryStructure directoryStructure = new DirectoryStructure(resolver);

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(resolver.getWorkDirectoryPath()));
    }

    @Test
    public void shouldInitializeApplicationDirectoryStructure() throws Exception {
        directoryStructure.init();

        assertWorkspaceDirectoryExists();
        assertCollectionsDirectoryExists();
    }

    @Test
    public void shouldCreateCollectionsDirectory() throws Exception {
        directoryStructure.createCollectionsDirectory();

        assertCollectionsDirectoryExists();
    }

    private void assertWorkspaceDirectoryExists() {
        assertTrue(Files.exists(Paths.get(resolver.getWorkDirectoryPath())));
    }

    private void assertCollectionsDirectoryExists() {
        assertTrue(Files.exists(Paths.get(resolver.getCollectionsPath())));
    }

}
