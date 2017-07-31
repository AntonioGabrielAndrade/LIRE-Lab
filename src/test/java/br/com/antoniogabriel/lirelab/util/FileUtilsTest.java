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