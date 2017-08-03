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

package net.lirelab.acceptance.custom;

import net.lirelab.collection.Collection;
import javafx.scene.Node;
import net.lirelab.test_utilities.AsyncUtils;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.AsyncUtils.waitUntilIsNotVisible;
import static net.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class CollectionTreeViewObject extends FxRobot {

    public static final String COLLECTION_TREE = "#collection-tree";

    public void checkCollectionsAreListed(Collection... collections) {
        for (Collection collection : collections) {
            verifyThat(collection.getName(), isVisible());
        }
    }

    public void waitUntilCollectionsAreListed(Collection... collections) throws TimeoutException {
        for (Collection collection : collections) {
            waitUntilCollectionIsListed(collection);
        }
    }

    public void waitUntilCollectionIsListed(Collection collection) throws TimeoutException {
        AsyncUtils.waitUntilIsVisible(collection.getName(), COLLECTION_TREE);
    }

    public void waitUntilCollectionIsListed(String collection) throws TimeoutException {
        AsyncUtils.waitUntilIsVisible(collection, COLLECTION_TREE);
    }

    public void waitUntilCollectionIsNotListed(Collection collection) throws TimeoutException {
        AsyncUtils.waitUntilIsNotVisible(collection.getName(), COLLECTION_TREE);
    }

    public void expandCollection(Collection collection) throws TimeoutException {
        Node arrow = null;

        //hack for when the robot cant find the arrow at first
        while(arrow == null) {
            arrow = from(lookup(COLLECTION_TREE))
                    .lookup(collection.getName()).lookup(".arrow").query();
        }

        clickOn(arrow).interrupt();
    }

    public void waitUntilImagesAreListed(String... imagesFileNames) throws TimeoutException {
        for (String imageFileName : imagesFileNames) {
            waitUntilImageIsListed(imageFileName);
        }
    }

    public void waitUntilImageIsListed(String imageFileName) throws TimeoutException {
        AsyncUtils.waitUntilIsVisible(imageFileName, COLLECTION_TREE);
    }

    public void selectCollection(Collection collection) {
        clickOn(collection.getName()).interrupt();
    }

    public void selectCollection(String collection) {
        clickOn(collection).interrupt();
    }

    public void selectImage(String image) {
        clickOn(image).interrupt();
    }

    public void openContextMenu(Collection collection) throws TimeoutException {
        rightClickOn(collection.getName()).interrupt();
        AsyncUtils.waitUntilIsVisible("#collection-context-menu");
    }

    public void deleteCollection(Collection collection) {
        Node deleteCollection = from(lookup("#collection-context-menu")).lookup("Delete collection").query();
        clickOn(deleteCollection);
    }
}
