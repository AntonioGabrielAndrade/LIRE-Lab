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

import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import net.lirelab.custom.image_grid.ImageGrid;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String THUMBNAIL_PATH = "some/thumbnail/path";
    private static final String IMAGE_PATH = "some/image/path";
    private static final Image IMAGE = new Image(IMAGE_PATH, THUMBNAIL_PATH);

    @Mock private ImageGrid imageGrid;
    @Mock private ImageView imageView;
    @Mock private ImageClickHandler imageClickHandler;
    @Mock private EventHandlerFactory eventHandlerFactory;
    @Mock private EventHandler<MouseEvent> eventHandler;
    @Mock private ToolTipProvider toolTipProvider;

    private Collection collection = new Collection("A Collection");

    @InjectMocks
    private CollectionGrid collectionGrid = new CollectionGrid();

    @Before
    public void setUp() throws Exception {
        collection.setImages(asList(IMAGE, IMAGE, IMAGE));
        given(imageGrid.addImage(THUMBNAIL_PATH)).willReturn(imageView);
    }

    @Test
    public void shouldAddThumbnailsToGridAndDisplayOriginalImageWhenClick() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), any(DisplayImageDialogHandler.class)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection);

        verify(imageView, times(3)).setOnMouseClicked(eventHandler);
    }

    @Test
    public void shouldAddPopOverToImage() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), any(DisplayImageDialogHandler.class)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection);

        verify(toolTipProvider, times(3)).setPopOver(imageView, IMAGE);
    }

    @Test
    public void shouldClearImagesWhenSetCollection() throws Exception {
        collectionGrid.setCollection(collection);

        verify(imageGrid).clear();
    }

    @Test
    public void shouldRegisterImageClickHandlerWithCollectionImages() throws Exception {
        given(eventHandlerFactory.createFrom(any(Image.class), eq(imageClickHandler)))
                .willReturn(eventHandler);

        collectionGrid.setCollection(collection, imageClickHandler);

        verify(imageView, times(3)).setOnMouseClicked(eventHandler);
    }
}