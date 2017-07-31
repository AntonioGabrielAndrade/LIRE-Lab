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

import java.io.IOException;
import java.util.List;

public class ThumbnailsCreator {

    private ThumbnailsCreatorCallback callback = new DumbThumbnailsCreatorCallback();
    private ThumbnailBuilder builder;
    private String thumbnailsDir;
    private List<String> images;
    private int thumbnailHeight;

    public ThumbnailsCreator(ThumbnailBuilder builder,
                             String thumbnailsDir,
                             List<String> paths,
                             int thumbnailHeight) {

        this.builder = builder;
        this.thumbnailsDir = thumbnailsDir;
        this.images = paths;
        this.thumbnailHeight = thumbnailHeight;
    }

    public void create() throws IOException {
        builder.createDirectory(thumbnailsDir);

        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i);
            callback.beforeCreateThumbnail(i+1, images.size(), image);
            builder.createThumbnail(image, thumbnailsDir, thumbnailHeight);
            callback.afterCreateThumbnail(i+1, images.size(), image);
        }

        callback.afterCreateAllThumbnails(images.size());
    }

    public void setCallback(ThumbnailsCreatorCallback callback) {
        this.callback = callback;
    }

    private class DumbThumbnailsCreatorCallback implements ThumbnailsCreatorCallback {
        @Override
        public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {}

        @Override
        public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {}

        @Override
        public void afterCreateAllThumbnails(int totalImages) {}
    }
}
