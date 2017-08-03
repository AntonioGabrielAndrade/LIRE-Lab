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

import net.lirelab.custom.dialog_header.DialogHeader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class DialogHeaderAcceptanceTest extends ApplicationTest {

    private DialogHeader dialogHeader;

    @Override
    public void start(Stage stage) throws Exception {
        dialogHeader = new DialogHeader();
        dialogHeader.setTitle("Dialog Title");
        dialogHeader.setHint("Some dialog hint here");


        BorderPane root = new BorderPane();
        root.setTop(dialogHeader);

        StackPane center = new StackPane(new Text("dialog content"));
        center.setPrefHeight(500);

        root.setCenter(center);
        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowTitle() throws Exception {
        verifyThat("#dialog-header-title", hasText("Dialog Title"));
    }

    @Test
    public void shouldShowHint() throws Exception {
        verifyThat("#dialog-header-hint", hasText("Some dialog hint here"));
    }
}
