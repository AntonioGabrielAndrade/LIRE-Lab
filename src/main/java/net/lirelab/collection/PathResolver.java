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

package net.lirelab.collection;

import java.io.File;

public class PathResolver {
    public static final String LIRELAB_WORK_DIRECTORY = "/lirelab";
    public static final String COLLECTIONS_DIRECTORY = "/collections";
    public static final String INDEX_DIRECTORY = "/index";
    public static final String THUMBNAILS_DIRECTORY = "/thumbnails";
    public static final String COLLECTION_XML = "collection.xml";

    public static final String HOME_DIRECTORY_PATH = System.getProperty("user.home");

    private final String applicationBasePath;

    public PathResolver() {
        this.applicationBasePath = HOME_DIRECTORY_PATH;
    }

    public PathResolver(String applicationBasePath) {
        this.applicationBasePath = new File(applicationBasePath).getAbsolutePath();
    }

    public String getCollectionPath(String collectionName) {
        return getCollectionsPath() + "/" + collectionName;
    }

    public String getCollectionsPath() {
        return getApplicationBasePath() + LIRELAB_WORK_DIRECTORY + COLLECTIONS_DIRECTORY;
    }

    public String getApplicationBasePath() {
        return applicationBasePath;
    }

    public String getWorkDirectoryPath() {
        return getApplicationBasePath() + LIRELAB_WORK_DIRECTORY;
    }

    public String getCollectionXMLPath(String collectionName) {
        return getCollectionsPath() + "/" + collectionName + "/" + COLLECTION_XML;
    }

    public String getIndexDirectoryPath(String collectionName) {
        return getCollectionPath(collectionName) + INDEX_DIRECTORY;
    }

    public String getThumbnailsDirectoryPath(String collectionName) {
        return getCollectionPath(collectionName) + THUMBNAILS_DIRECTORY;
    }
}
