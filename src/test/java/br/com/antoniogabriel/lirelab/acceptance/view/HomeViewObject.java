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

package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.custom.CollectionTreeViewObject;
import br.com.antoniogabriel.lirelab.collection.Collection;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class HomeViewObject extends FxRobot {

    private CollectionTreeViewObject treeViewObject = new CollectionTreeViewObject();


    public void waitUntilCollectionsAreListed(Collection... collections) throws TimeoutException {
        treeViewObject.waitUntilCollectionsAreListed(collections);
    }

    public void waitUntilCollectionIsListed(Collection collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsListed(collection);
    }

    public void waitUntilCollectionIsListed(String collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsListed(collection);
    }

    public void expandCollection(Collection collection) throws TimeoutException {
        treeViewObject.expandCollection(collection);
    }

    public void waitUntilImageIsListed(String imageFileName) throws TimeoutException {
        treeViewObject.waitUntilImageIsListed(imageFileName);
    }

    public void waitUntilCollectionIsNotListed(Collection collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsNotListed(collection);
    }

    public void selectCollection(Collection collection) {
        treeViewObject.selectCollection(collection);
    }

    public void waitUntilImageIsVisible(String imageName) throws TimeoutException {
        waitUntilIsVisible("#" + imageName, "#center-pane");
    }

    public void selectImage(String imageName) {
        treeViewObject.selectImage(imageName);
    }

    public HomeViewObject deleteByContextMenu(Collection collection) throws TimeoutException {
        treeViewObject.openContextMenu(collection);
        treeViewObject.deleteCollection(collection);
        return this;
    }

    public void ok() {
        clickOn("OK").interrupt();
    }
}
