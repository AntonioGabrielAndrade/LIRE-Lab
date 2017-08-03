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

package net.lirelab.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

public class ImageViewFactory {

    public ImageView create(String path) {
        return create(path, true);
    }

    public ImageView create(String path, boolean backgroundLoading) {
        ImageView imageView = createImageView(path, backgroundLoading);
        String id = createId(path);
        imageView.setId(id);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    private String createId(String path) {
        String filename = getFilename(path);
        String id = removeExtensions(filename, ".thumbnail", ".jpg");

        return id;
    }

    private String removeExtensions(String filename, String... extensions) {
        for (String extension : extensions) {
            filename = filename.replace(extension, "");
        }
        return filename;
    }

    private String getFilename(String path) {
        return Paths.get(path).getFileName().toString();
    }

    private ImageView createImageView(String path, boolean backgroundLoading) {
        Image image = new Image("file://" + path, backgroundLoading);
        return new ImageView(image);
    }
}
