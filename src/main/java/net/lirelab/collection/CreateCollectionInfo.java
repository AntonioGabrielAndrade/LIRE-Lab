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

import net.lirelab.lire.Feature;

import java.util.List;

public class CreateCollectionInfo {
    private final String collectionName;
    private final String collectionDescription;
    private final String imagesDirectory;
    private final List<Feature> features;
    private final boolean scanSubdirectories;
    private final int thumbnailsHeight;
    private final boolean useParallelIndexer;
    private final int numberOfThreads;

    public CreateCollectionInfo(String collectionName,
                                String collectionDescription,
                                String imagesDirectory,
                                List<Feature> features,
                                boolean scanSubdirectories,
                                int thumbnailsHeight,
                                boolean useParallelIndexer,
                                int numberOfThreads) {

        this.collectionName = collectionName;
        this.collectionDescription = collectionDescription;
        this.imagesDirectory = imagesDirectory;
        this.features = features;
        this.scanSubdirectories = scanSubdirectories;
        this.thumbnailsHeight = thumbnailsHeight;
        this.useParallelIndexer = useParallelIndexer;
        this.numberOfThreads = numberOfThreads;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public boolean isScanSubdirectories() {
        return scanSubdirectories;
    }

    public int getThumbnailsHeight() {
        return thumbnailsHeight;
    }

    public boolean isUseParallelIndexer() {
        return useParallelIndexer;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }
}
