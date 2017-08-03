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

package net.lirelab.collection;

import net.lirelab.lire.SimpleIndexCreator;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionTaskTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private CreateCollectionTask task;

    @Mock private SimpleIndexCreator indexCreator;
    @Mock private ThumbnailsCreator thumbnailsCreator;
    @Mock private XMLCreator xmlCreator;

    @Before
    public void setUp() throws Exception {
        task = new CreateCollectionTask(new CreateCollectionRunner(indexCreator, thumbnailsCreator, xmlCreator));
    }

    @Test
    public void shouldSetItselfAsCallback() throws Exception {
        verify(indexCreator).setCallback(task);
        verify(thumbnailsCreator).setCallback(task);
        verify(xmlCreator).setCallback(task);
    }

    @Test
    public void shouldCreateCollection() throws Exception {
        task.call();

        verify(indexCreator).create();
        verify(thumbnailsCreator).create();
        verify(xmlCreator).create();
    }
}
