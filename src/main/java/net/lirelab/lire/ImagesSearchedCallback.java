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

package net.lirelab.lire;

import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import net.lirelab.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

class ImagesSearchedCallback implements IndexSearcherCallback {

    private CollectionUtils collectionUtils;
    private Collection collection;

    private List<Image> images = new ArrayList<>();

    public ImagesSearchedCallback(CollectionUtils collectionUtils, Collection collection) {
        this.collectionUtils = collectionUtils;
        this.collection = collection;
    }

    @Override
    public void imageSearched(String imgPath, int position, double score) {
        Image image = createImage(collection, imgPath);
        image.setScore(score);
        image.setPosition(position);
        images.add(image);
    }

    public List<Image> getImages() {
        return images;
    }

    private Image createImage(Collection collection, String fileName) {
        return new Image(fileName, collectionUtils.getThumbnailPathFromImagePath(collection, fileName));
    }
}
