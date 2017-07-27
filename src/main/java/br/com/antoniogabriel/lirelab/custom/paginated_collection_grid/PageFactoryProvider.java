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

package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.util.Callback;

import java.util.List;

class PageFactoryProvider {

    public Callback<Integer, Node> getPageFactory(List<Image> images,
                                                  int pageSize,
                                                  ImageClickHandler imageClickHandler,
                                                  DoubleProperty gridGap,
                                                  DoubleProperty imageHeight) {

        return new CollectionGridPageFactory(images, pageSize, imageClickHandler, gridGap, imageHeight);
    }
}
