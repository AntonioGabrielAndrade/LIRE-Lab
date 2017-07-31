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

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.CollectionGrid;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

class CollectionGridPageFactory implements Callback<Integer, Node> {

    private final List<Image> images;
    private final int pageSize;
    private final ImageClickHandler clickHandler;
    private DoubleProperty gridGap;
    private DoubleProperty imageHeight;

    public CollectionGridPageFactory(List<Image> images,
                                     int pageSize,
                                     ImageClickHandler clickHandler,
                                     DoubleProperty gridGap,
                                     DoubleProperty imageHeight) {

        this.images = images;
        this.pageSize = pageSize;
        this.clickHandler = clickHandler;
        this.gridGap = gridGap;
        this.imageHeight = imageHeight;
    }

    @Override
    public Node call(Integer pageIndex) {
        return createPage(pageIndex);
    }

    private CollectionGrid createPage(int pageIndex) {
        try {

            CollectionGrid page = createCollectionGrid();
            page.bindGapsTo(gridGap);
            page.bindImageHeightTo(imageHeight);

            int fromIndex = indexOfFirstImageInPage(pageIndex);
            int toIndex = indexOfLastImageInPage(pageIndex) + 1;

            page.setImages(imagesInRange(fromIndex, toIndex), clickHandler);

            return page;

        } catch (IOException e) {
            throw new LireLabException("Could not create grid", e);
        }
    }

    private int indexOfLastImageInPage(int pageIndex) {
        return lastPossibleIndexInPage(pageIndex) < collectionLastIndex() ?
                lastPossibleIndexInPage(pageIndex) :
                collectionLastIndex();
    }

    private int lastPossibleIndexInPage(int pageIndex) {
        return indexOfFirstImageInPage(pageIndex) + (pageSize - 1);
    }

    private int collectionLastIndex() {
        return images.size() - 1;
    }

    private int indexOfFirstImageInPage(int pageIndex) {
        return pageIndex * pageSize;
    }

    private List<Image> imagesInRange(int fromIndex, int toIndex) {
        return images.subList(fromIndex, toIndex);
    }

    protected CollectionGrid createCollectionGrid() {
        return new CollectionGrid();
    }
}
