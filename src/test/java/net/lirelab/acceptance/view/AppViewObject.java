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

import net.lirelab.collection.Collection;
import javafx.scene.control.ComboBox;
import net.lirelab.test_utilities.AsyncUtils;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class AppViewObject extends FxRobot {

    private static final String ABOUT_ROOT = "#about-root";

    private MenuViewObject menuView = new MenuViewObject();
    private ToolBarViewObject toolBarView = new ToolBarViewObject();
    private WelcomeViewObject welcomeView = new WelcomeViewObject();

    public void checkMenus() {
        menuView.checkMenuBar();
        menuView.checkFileMenu();
        menuView.checkHelpMenu();
    }

    public void checkToolBar() {
        toolBarView.checkStructure();
    }

    public void checkWelcomeView() {
        welcomeView.checkStructure();
    }

    public CreateCollectionViewObject createCollection() {
        clickOn("#toolbar-create-collection").interrupt();

        return new CreateCollectionViewObject();
    }

    public void checkCollectionIsListed(String collectionName) throws TimeoutException {
        verifyThat("#collection-tree", isVisible());
        waitFor(isPresent(collectionName));
    }

    private void waitFor(Callable<Boolean> present) throws TimeoutException {
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, present);
    }

    private Callable<Boolean> isPresent(String text) {
        return () -> lookup(text).tryQuery().isPresent();
    }

    public SearchViewObject search() {
        clickOn("#toolbar-search-collection");
        return new SearchViewObject();
    }

    public AppViewObject openAboutDialog() {
        clickOn("#toolbar-about");
        return this;
    }

    public void checkAboutDialog() throws TimeoutException {
        AsyncUtils.waitUntilIsVisible("#splash-image", ABOUT_ROOT);
        AsyncUtils.waitUntilIsVisible("#about-info", ABOUT_ROOT);
    }

    public void selectCollectionToRun(String collectionName) throws TimeoutException {
        ComboBox<Collection> comboBox = lookup("#collections-combo-box").query();
        clickOn(comboBox);

        for (Collection collection : comboBox.getItems()) {
            if(collection.getName().equals(collectionName)) {
                interact(() -> comboBox.getSelectionModel().select(collection));
            }
        }
    }
}
