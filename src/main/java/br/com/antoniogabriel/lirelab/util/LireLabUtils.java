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

import br.com.antoniogabriel.lirelab.collection.PathResolver;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LireLabUtils {

    private PathResolver resolver;
    private FileUtils fileUtils;

    @Inject
    public LireLabUtils(PathResolver resolver, FileUtils fileUtils) {
        this.resolver = resolver;
        this.fileUtils = fileUtils;
    }

    public boolean collectionsDirectoryExist() {
        return fileUtils.fileExists(resolver.getCollectionsPath());
    }

    public boolean isCollection(Path dir) {
        return fileUtils.fileExists(dir.toAbsolutePath().toString() + "/" + PathResolver.COLLECTION_XML);
    }

    public Path getCollectionsPath() {
        return Paths.get(resolver.getCollectionsPath());
    }

    public Path getCollectionPath(String collectionName) {
        return Paths.get(resolver.getCollectionPath(collectionName));
    }

    public Path getCollectionXMLPath(String collectionName) {
        return Paths.get(resolver.getCollectionXMLPath(collectionName));
    }

    public void deleteCollection(String collectionName) throws IOException {
        Path dirPath = getCollectionPath(collectionName);
        Path xmlPath = getCollectionXMLPath(collectionName);

        Files.delete(xmlPath);
        org.apache.commons.io.FileUtils.deleteDirectory(dirPath.toFile());
    }
}
