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

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import net.semanticmetadata.lire.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ThumbnailBuilder {

    public void createDirectory(String thumbnailsDir) throws IOException {
        Path path = Paths.get(thumbnailsDir);
        if(!Files.exists(path))
            Files.createDirectory(path);
    }

    public List<String> getAllImagePaths(String imagesDir) throws IOException {
        return FileUtils.getAllImages(new File(imagesDir), true);
    }

    public void createThumbnail(String srcImagePath, String thumbnailsDir, int thumbnailHeight) throws IOException {
        Thumbnails.of(srcImagePath)
                .height(thumbnailHeight)
                .toFiles(new File(thumbnailsDir), Rename.SUFFIX_DOT_THUMBNAIL);
    }
}