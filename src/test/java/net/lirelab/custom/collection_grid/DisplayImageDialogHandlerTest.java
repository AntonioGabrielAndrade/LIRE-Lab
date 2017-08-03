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

package net.lirelab.custom.collection_grid;

import net.lirelab.collection.DialogProvider;
import net.lirelab.collection.Image;
import net.lirelab.custom.image_dialog.ImageDialog;
import net.lirelab.util.FileUtils;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DisplayImageDialogHandlerTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String IMAGE_PATH = "some/image/path";
    private static final String THUMBNAIL_PATH = "some/thumbnail/path";

    private static final Image IMAGE = new Image(IMAGE_PATH, THUMBNAIL_PATH);

    @Mock private ImageDialog originalImageDialog;
    @Mock private ImageDialog thumbnailDialog;
    @Mock private MouseEvent event;
    @Mock private DialogProvider dialogProvider;
    @Mock private Window window;
    @Mock private FileUtils fileUtils;

    private DisplayImageDialogHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new DisplayImageDialogHandler(dialogProvider, fileUtils);
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(dialogProvider.getImageDialog(IMAGE_PATH, window)).willReturn(originalImageDialog);
        given(dialogProvider.getImageDialog(THUMBNAIL_PATH, window)).willReturn(thumbnailDialog);
    }

    @Test
    public void shouldShowImageInDialogWhenImagePathExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(true);

        handler.handle(IMAGE, event);

        verify(originalImageDialog).show();
    }

    @Test
    public void shouldShowThumbnailInDialogWhenImagePathDontExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(false);

        handler.handle(IMAGE, event);

        verify(thumbnailDialog).show();
    }
}