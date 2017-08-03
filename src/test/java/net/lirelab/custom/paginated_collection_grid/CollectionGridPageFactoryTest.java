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

package net.lirelab.custom.paginated_collection_grid;

import net.lirelab.collection.Image;
import net.lirelab.custom.collection_grid.CollectionGrid;
import net.lirelab.custom.collection_grid.ImageClickHandler;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionGridPageFactoryTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private CollectionGrid page;
    @Mock private ImageClickHandler handler;
    @Mock private List<Image> images;
    @Mock private List<Image> imagesSubSet;

    @Test
    public void shouldReturnCollectionGridWithImagesSubsetForInformedPage() throws Exception {
        TestableCollectionGridPageFactory factory;
        int pageSize = 10;
        int totalImages = 95;

        factory = getFactory(pageSize, totalImages);

        factory.call(0);
        verify(images).subList(0, 10);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(1);
        verify(images).subList(10, 20);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(6);
        verify(images).subList(60, 70);
        verify(page).setImages(imagesSubSet, handler);

        factory = getFactory(pageSize, totalImages);

        factory.call(9);
        verify(images).subList(90, 95);
        verify(page).setImages(imagesSubSet, handler);
    }

    private TestableCollectionGridPageFactory getFactory(int pageSize, int totalImages) {
        reset(page);

        given(images.subList(anyInt(), anyInt())).willReturn(imagesSubSet);
        given(images.size()).willReturn(totalImages);

        return new TestableCollectionGridPageFactory(images, pageSize, handler);
    }

    private class TestableCollectionGridPageFactory extends CollectionGridPageFactory {

        public TestableCollectionGridPageFactory(List<Image> images, int pageSize, ImageClickHandler handler) {
            super(images, pageSize, handler, null, null);
        }

        @Override
        protected CollectionGrid createCollectionGrid() {
            return page;
        }
    }
}