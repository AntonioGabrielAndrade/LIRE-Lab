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

package net.lirelab.util;

import net.lirelab.collection.Collection;
import net.lirelab.collection.PathResolver;
import net.coobird.thumbnailator.name.Rename;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class CollectionUtils {


    private PathResolver resolver;

    @Inject
    public CollectionUtils(PathResolver resolver) {
        this.resolver = resolver;
    }

    public List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), false);
    }

    public String getThumbnailPathFromImagePath(Collection collection, String imagePath) {
        String thumbnailsDirectoryPath = resolver.getThumbnailsDirectoryPath(collection.getName());
        String originalImageFilename = Paths.get(imagePath).getFileName().toString();
        String thumbnailFilename = Rename.SUFFIX_DOT_THUMBNAIL.apply(originalImageFilename, null);
        return thumbnailsDirectoryPath + "/" + thumbnailFilename;
    }
}
