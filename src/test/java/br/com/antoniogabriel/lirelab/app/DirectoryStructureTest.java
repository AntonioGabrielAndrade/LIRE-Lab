/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;
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
