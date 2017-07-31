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

package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;

public class ImageDialogAcceptanceTest extends ApplicationTest {

    private ImageDialog dialog;
    private ImageDialogViewObject view = new ImageDialogViewObject();

    @Override
    public void start(Stage stage) throws Exception {}

    @Before
    public void setUp() throws Exception {
        interact(() -> {
            dialog = new ImageDialog(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg");
            dialog.show();
        });
    }

    @After
    public void tearDown() throws Exception {
        interact(() -> {
            dialog.close();
        });
    }

    @Test
    public void shouldShowImage() throws Exception {
        view.checkImageIsDisplayed("14474347006_99aa0fd981_k");
    }

    @Test
    public void shouldShowOkButton() throws Exception {
        view.close();
    }
}
