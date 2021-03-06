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

package net.lirelab.acceptance;

import net.lirelab.collection.*;
import net.lirelab.lire.Feature;
import net.lirelab.util.CollectionUtils;
import net.lirelab.util.LireLabUtils;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static net.lirelab.lire.Feature.CEDD;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionTestHelper {

    private final PathResolver resolver;
    private final net.lirelab.util.FileUtils fileUtils;
    private final CollectionUtils collectionUtils;
    private final CollectionAssembler collectionAssembler;
    private final LireLabUtils lireLabUtils;
    private final CollectionRepository collectionRepository;

    @Inject
    public CollectionTestHelper(PathResolver resolver) {
        this.resolver = resolver;
        this.fileUtils = new net.lirelab.util.FileUtils();
        this.lireLabUtils = new LireLabUtils(resolver, fileUtils);
        this.collectionUtils = new CollectionUtils(resolver);
        this.collectionAssembler = new CollectionAssembler(resolver, collectionUtils);
        this.collectionRepository = new CollectionRepository(lireLabUtils, collectionAssembler);
    }

    public void checkCollectionExists(String collection) {
        assertTrue(rootFolderExist(collection));
        assertTrue(indexFolderExist(collection));
        assertTrue(thumbnailsFolderExist(collection));
        assertTrue(xmlFileExist(collection));
    }

    public void checkCollectionDontExists(String collection) {
        assertFalse(rootFolderExist(collection));
        assertFalse(indexFolderExist(collection));
        assertFalse(thumbnailsFolderExist(collection));
        assertFalse(xmlFileExist(collection));
    }

    public void deleteCollection(Collection collection) {
        deleteCollection(collection.getName());
    }

    public void deleteCollection(String collectionName) {
        try {
            File directory = new File(resolver.getCollectionPath(collectionName));
            File renamed = new File(System.getProperty("java.io.tmpdir") + "/" + collectionName);

            if(!directory.exists()) return;

            FileUtils.moveDirectory(directory, renamed);
            FileUtils.deleteDirectory(renamed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createStubCollection(Collection collection) throws IOException, JAXBException {
        createRootFolder(collection);
        createIndexFolder(collection);
        createThumbnailsFolder(collection);
        createXMLFile(collection);
    }

    public void createRealCollection(String name, String imagesPath, Feature... features) {
        List<Feature> collectionFeatures = features.length > 0 ? asList(features) : asList(CEDD);

        CreateCollectionRunner runner =
                new CreateCollectionRunnerFactory(resolver, new net.lirelab.util.FileUtils())
                        .getCreateRunner(new CreateCollectionInfo(name,
                                                                "",
                                                                imagesPath,
                                                                collectionFeatures,
                                                                true,
                                                                100,
                                                                false,
                                                                1));

        runner.run();
    }

    public Collection readCollection(String name) {
        return collectionRepository.getCollection(name);
    }

    private boolean xmlFileExist(String collection) {
        return fileExists(resolver.getCollectionXMLPath(collection));
    }

    private boolean thumbnailsFolderExist(String collection) {
        return directoryExists(resolver.getThumbnailsDirectoryPath(collection));
    }

    private boolean indexFolderExist(String collection) {
        return directoryExists(resolver.getIndexDirectoryPath(collection));
    }

    private boolean rootFolderExist(String collection) {
        return directoryExists(resolver.getCollectionPath(collection));
    }

    private boolean directoryExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isDirectory(Paths.get(path));
    }

    private boolean fileExists(String path) {
        return Files.exists(Paths.get(path)) && Files.isRegularFile(Paths.get(path));
    }

    private Path createRootFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getCollectionPath(collection.getName())));
    }

    private Path createThumbnailsFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getThumbnailsDirectoryPath(collection.getName())));
    }

    private Path createIndexFolder(Collection collection) throws IOException {
        return Files.createDirectories(Paths.get(resolver.getIndexDirectoryPath(collection.getName())));
    }

    private void createXMLFile(Collection collection) throws JAXBException {
        CollectionXMLDAO collectionDao = new CollectionXMLDAO(new File(resolver.getCollectionPath(collection.getName())));
        collectionDao.create(collection);
    }
}
