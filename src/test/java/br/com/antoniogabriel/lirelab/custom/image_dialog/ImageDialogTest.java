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

package br.com.antoniogabriel.lirelab.custom.image_dialog;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog.MAX_IMAGE_HEIGHT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.runOnFxThreadAndWait;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageDialogTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String IMAGE_PATH = "some/image/path";

    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private DialogActions dialogActions;
    @Mock private ImageViewConfig imageViewConfig;
    @Mock private BorderPane contentRoot;

    private ImageDialog dialog;

    @Before
    public void setUp() throws Exception {
        given(imageViewFactory.create(IMAGE_PATH, false)).willReturn(imageView);
        createDialog();
    }

    @Test
    public void shouldSetImageAsContentWhenInitialize() throws Exception {
        verify(contentRoot).setCenter(imageView);
        verify(dialogActions).setContent(contentRoot);
    }

    @Test
    public void shouldAddOkButtonWhenInitialize() throws Exception {
        verify(dialogActions).addButtonType(ButtonType.OK);
    }

    @Test
    public void shouldLimitImageHeightToAMaximum() throws Exception {
        verify(imageViewConfig).limitImageHeight(imageView, MAX_IMAGE_HEIGHT);
    }

    @Test
    public void shouldSetDialogTitleAsImagePath() throws Exception {
        verify(dialogActions).setTitle(IMAGE_PATH);
    }

    @Test
    public void shouldSetDialogAsResizable() throws Exception {
        verify(dialogActions).setResizable(true);
    }

    private void createDialog() {
        runOnFxThreadAndWait(() -> dialog =
                new ImageDialog(IMAGE_PATH,
                                imageViewFactory,
                                imageViewConfig,
                                dialogActions,
                                contentRoot));
    }
}