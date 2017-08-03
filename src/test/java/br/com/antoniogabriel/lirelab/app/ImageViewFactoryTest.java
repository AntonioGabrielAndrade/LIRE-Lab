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

package br.com.antoniogabriel.lirelab.app;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.IMAGE2;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ImageViewFactoryTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String EXISTENT_FILE_PATH = "thumb/16903390174_1d670a5849_h.thumbnail.jpg";
    private static final String EXISTENT_FILE_NAME_WITH_NO_EXTENSIONS = IMAGE2;

    private ImageViewFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ImageViewFactory();
    }

    @Test
    public void shouldCreateImageViewWhenFileExists() throws Exception {
        ImageView imageView = factory.create(existentFilePath());
        assertNotNull(imageView);
    }

    @Test
    public void shouldSetIdAsFilenameWithoutThumbnailAndJPGExtension() throws Exception {
        ImageView imageView = factory.create(existentFilePath());
        assertThat(imageView.getId(), equalTo(EXISTENT_FILE_NAME_WITH_NO_EXTENSIONS));
    }

    @Test
    public void shouldSetupImageViewToPreserveRatio() throws Exception {
        ImageView imageView = factory.create(existentFilePath());
        assertTrue(imageView.isPreserveRatio());
    }

    private String existentFilePath() {
        return getClass().getResource(EXISTENT_FILE_PATH).getFile();
    }
}