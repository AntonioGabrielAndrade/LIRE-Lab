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

package net.lirelab.custom;

import net.lirelab.app.ImageViewFactory;
import net.lirelab.custom.image_grid.ImageGrid;
import net.lirelab.custom.image_grid.ImageGridBuilder;
import net.lirelab.util.FileUtils;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.inject.Inject;

public class LireLabBuiderFactory implements BuilderFactory {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;

    @Inject
    public LireLabBuiderFactory(ImageViewFactory imageViewFactory, FileUtils fileUtils) {
        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        if(type == ImageGrid.class) {
            return new ImageGridBuilder(imageViewFactory, fileUtils);
        }

        return new JavaFXBuilderFactory().getBuilder(type);
    }
}
