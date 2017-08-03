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

import net.lirelab.app.LireLabException;
import net.lirelab.lire.*;
import net.lirelab.util.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateCollectionRunnerFactory {

    private PathResolver resolver;
    private FileUtils fileUtils;

    @Inject
    public CreateCollectionRunnerFactory(PathResolver resolver, FileUtils fileUtils) {
        this.resolver = resolver;
        this.fileUtils = fileUtils;
    }

    public CreateCollectionRunner getCreateRunner(CreateCollectionInfo createInfo) {
        String collectionPath = resolver.getCollectionPath(createInfo.getCollectionName());
        String indexPath = resolver.getIndexDirectoryPath(createInfo.getCollectionName());
        String thumbnailsPath = resolver.getThumbnailsDirectoryPath(createInfo.getCollectionName());


        LIRE lire = new LIRE();
        List<String> paths;

        try {
            paths = fileUtils.getAllImagesPaths(createInfo.getImagesDirectory(), createInfo.isScanSubdirectories());
        } catch (IOException e) {
            throw new LireLabException("Could not read paths", e);
        }

        IndexCreator indexCreator = getIndexCreator(lire,
                indexPath,
                createInfo.getImagesDirectory(),
                paths,
                createInfo.getFeatures(),
                createInfo.isUseParallelIndexer(),
                createInfo.getNumberOfThreads());

        ThumbnailBuilder thumbnailBuilder = new ThumbnailBuilder();
        ThumbnailsCreator thumbnailsCreator = new ThumbnailsCreator(thumbnailBuilder,
                                                                    thumbnailsPath,
                                                                    paths,
                                                                    createInfo.getThumbnailsHeight());

        CollectionXMLDAO xmlDAO = new CollectionXMLDAO(new File(collectionPath));
        XMLCreator xmlCreator = new XMLCreator(createInfo.getCollectionName(),
                                                            createInfo.getImagesDirectory(),
                                                            createInfo.getFeatures(),
                                                            xmlDAO);

        return new CreateCollectionRunner(indexCreator, thumbnailsCreator, xmlCreator);
    }

    private IndexCreator getIndexCreator(LIRE lire,
                                         String indexPath,
                                         String imagesPath,
                                         List<String> paths,
                                         List<Feature> collectionFeatures,
                                         boolean useParallelIndexer,
                                         int numberOfThreads) {

        if(useParallelIndexer) {
            return new ParallelIndexCreator(lire,
                                            indexPath,
                                            collectionFeatures,
                                            imagesPath,
                                            numberOfThreads);
        } else {
            return new SimpleIndexCreator(lire,
                                            indexPath,
                                            collectionFeatures,
                                            paths);
        }
    }

}
