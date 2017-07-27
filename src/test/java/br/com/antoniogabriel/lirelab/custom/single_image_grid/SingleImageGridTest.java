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

package br.com.antoniogabriel.lirelab.custom.single_image_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SingleImageGridTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private ImageGrid imageGrid;

    @InjectMocks private SingleImageGrid singleImageGrid = new SingleImageGrid();

    @Test
    public void shouldSetImageHeight() throws Exception {
        int height = 100;

        singleImageGrid.setImageHeight(height);

        verify(imageGrid).setImagesHeight(height);
    }

    @Test
    public void shouldSetImage() throws Exception {
        String imagePath = "some/image/path";
        String thumbnailPath = "some/thumbnail/path";

        singleImageGrid.setImage(new Image(imagePath, thumbnailPath));

        verify(imageGrid).clear();
        verify(imageGrid).addImage(thumbnailPath);
    }

    @Test
    public void shouldSetOnChangeListener() throws Exception {
        BooleanHolder listenerExecuted = new BooleanHolder(false);

        singleImageGrid.setOnChange((image) -> listenerExecuted.setValue(true));

        singleImageGrid.setImage(new Image("", ""));

        assertTrue(listenerExecuted.getValue());
    }

    class BooleanHolder {
        boolean value = false;

        public BooleanHolder(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}