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
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.collection;
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