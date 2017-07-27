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

package br.com.antoniogabriel.lirelab.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ThumbnailsCreatorTest {

    private static final String IMG1 = "/some/img/path";
    private static final String IMG2 = "/other/img/path";

    private static final String THUMBNAILS_DIR = "/some/dir";

    private static final List<String> IMAGES = Arrays.asList(IMG1, IMG2);
    private static final int THUMBNAILS_HEIGHT = 100;

    @Mock private ThumbnailBuilder builder;
    @Mock private ThumbnailsCreatorCallback callback;

    private ThumbnailsCreator creator;
    private InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        setupThumbnailsCreator();
        setupInOrder();
    }

    private void setupThumbnailsCreator() {
        creator = new ThumbnailsCreator(builder, THUMBNAILS_DIR, IMAGES, THUMBNAILS_HEIGHT);
        creator.setCallback(callback);
    }

    private void setupInOrder() {
        inOrder = Mockito.inOrder(builder, callback);
    }

    @Test
    public void shouldCreateThumbnails() throws Exception {
        creator.create();

        inOrder.verify(builder).createDirectory(THUMBNAILS_DIR);

        inOrder.verify(callback).beforeCreateThumbnail(1, IMAGES.size(), IMG1);
        inOrder.verify(builder).createThumbnail(IMG1, THUMBNAILS_DIR, THUMBNAILS_HEIGHT);
        inOrder.verify(callback).afterCreateThumbnail(1, IMAGES.size(), IMG1);

        inOrder.verify(callback).afterCreateAllThumbnails(IMAGES.size());
    }
}
