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

package net.lirelab.acceptance.view;

import net.lirelab.acceptance.custom.CollectionGridViewObject;
import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import javafx.scene.control.TextField;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.AsyncUtils.*;

public class SearchViewObject extends FxRobot {

    public void checkImagesAreVisible(String... images) {
        new CollectionGridViewObject().checkImagesAreVisible(images);
    }

    public void waitUntilShowCollection(Collection collection) throws TimeoutException {
        for (Image image : collection.getImages()) {
            waitUntilIsVisible("#" + image.getImageName());
        }
    }

    public void selectQuery(String image) {
        clickOn("#" + image).interrupt();
    }

    public void waitUntilShowQuery(String image) throws TimeoutException {
        waitUntilIsVisible("#" + image, "#query");
    }

    public void waitUntilImagesAreOrderedLike(String... images) throws TimeoutException {
        waitUntilElementsAreOrderedLike("#output", ".image-view", images);
    }

    public void writeQueryPath(String path) {
        clickOn("#query-image-field").write("").interrupt().write(path);
    }

    public void setQueryPath(String path) {
        TextField field = lookup("#query-image-field").query();
        field.setText(path);
    }

    public void checkRunIsEnabled() throws TimeoutException {
        waitUntil(() -> !lookup("#run-loaded-image").query().isDisabled());
    }

    public void checkRunIsDisabled() throws TimeoutException {
        waitUntil(() -> lookup("#run-loaded-image").query().isDisabled());
    }

    public void run() throws TimeoutException {
        checkRunIsEnabled();
        clickOn("#run-loaded-image").interrupt();
    }
}
