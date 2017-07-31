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

package br.com.antoniogabriel.lirelab.acceptance.fxml;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class FXMLLoadInStageAcceptanceTest extends ApplicationTest {

    private static FXMLLoader loader = new FXMLLoader();

    private FXMLImplForTests fxml;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        fxml = new FXMLImplForTests(loader);
        fxml.loadIn(stage);
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldLoadFXMLInStage() throws Exception {
        verifyThat("#fxml-impl-root", isVisible());
    }

    @Test
    public void shouldReturnController() throws Exception {
        FxmlImplForTestsController controller = fxml.getController();

        assertNotNull(controller);
    }

    @Test
    public void shouldLoadWithSameLoaderInstanceSeveralTimesInARow() throws Exception {
        closeAndLoadAgain();
        verifyThat("#fxml-impl-root", isVisible());

        closeAndLoadAgain();
        verifyThat("#fxml-impl-root", isVisible());
    }

    private void closeAndLoadAgain() {
        interact(() -> {
            stage.close();
            fxml.loadIn(stage);
        });
    }
}
