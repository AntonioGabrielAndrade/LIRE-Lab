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


import net.lirelab.acceptance.custom.ProgressDialogViewObject;
import net.lirelab.collection.CreateCollectionFXML;
import net.lirelab.lire.Feature;
import javafx.scene.control.CheckBox;
import javafx.stage.Window;
import net.lirelab.test_utilities.AsyncUtils;
import org.testfx.api.FxRobot;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.AsyncUtils.waitUntil;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.WindowMatchers.isNotShowing;

public class CreateCollectionViewObject extends FxRobot {

    private final Window window;

    public CreateCollectionViewObject() {
        window = window(CreateCollectionFXML.TITLE);
        targetWindow(window);
    }

    public void waitUntilWindowIsShowing() throws TimeoutException {
        AsyncUtils.waitUntil(() -> window.isShowing());
    }

    public void cancel() {
        clickOn("#cancel");
    }

    public void checkWindowIsClosed() {
        verifyThat(window, isNotShowing());
    }

    public void unselectAllFeatures() {
        for (CheckBox checkBox : tableCheckBoxes()) {
            checkBox.setSelected(false);
        }
    }

    private Set<CheckBox> tableCheckBoxes() {
        return lookup("#featuresTable")
                .lookup(".check-box")
                .queryAll();
    }

    public CreateCollectionViewObject writeName(String name) {
        clickOn("#collection-name").write(name);
        return this;
    }

    public CreateCollectionViewObject writeImagesDirectory(String path) {
        clickOn("#images-directory").write(path);
        return this;
    }

    public CreateCollectionViewObject selectFeatures(Feature... features) {
        for (Feature feature : features) {
            select(feature);
        }
        return this;
    }

    private void select(Feature feature) {
        CheckBox checkBox = checkboxFor(feature);
        boolean oldValue = checkBox.isSelected();

        clickOn(checkBox).interrupt();

        //workaround when sometimes it wont click the checkbox
        if(checkBox.isSelected() == oldValue) {
            checkBox.setSelected(!oldValue);
        }
    }

    private CheckBox checkboxFor(Feature feature) {
        return lookup("#featuresTable")
                .lookup(".table-row-cell").nth(feature.ordinal())
                .lookup(".table-cell").nth(0)
                .lookup(".check-box").query();
    }

    public void checkCreateIsDisabled() {
        verifyThat("#create", isDisabled());
    }

    public void checkCreateIsEnabled() {
        verifyThat("#create", isEnabled());
    }

    public ProgressDialogViewObject create() {
        clickOn("#create");
        return new ProgressDialogViewObject();
    }
}
