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

package net.lirelab.custom.image_grid;

import net.lirelab.app.ImageViewFactory;
import net.lirelab.util.FileUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageGridTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String INEXISTENT_PATH = "INEXISTENT_PATH";
    private static final String PATH1 = "PATH1";
    private static final String PATH2 = "PATH2";
    private static final String PATH3 = "PATH3";

    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private FileUtils fileUtils;
    @Mock private ObservableList<Node> children;
    @Mock private FlowPane flowPane;
    @Mock private SimpleIntegerProperty imagesHeight;
    @Mock private DoubleProperty fitHeightProperty;

    @InjectMocks private ImageGrid grid = new ImageGrid(imageViewFactory, fileUtils);

    @Before
    public void setUp() throws Exception {
        doReturn(children).when(flowPane).getChildren();
        doReturn(imageView).when(imageViewFactory).create(anyString()) ;
        doReturn(fitHeightProperty).when(imageView).fitHeightProperty() ;
    }

    @Test
    public void shouldNotAddImageWhenPathDontExist() throws Exception {
        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(false).when(fileUtils).fileExists(INEXISTENT_PATH);

        grid.setPaths(asList(PATH1, PATH2, INEXISTENT_PATH));

        verify(children, times(2)).add(imageView);
    }

    @Test
    public void shouldAddImagesWhenPathsExist() throws Exception {
        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(true).when(fileUtils).fileExists(PATH3);

        grid.setPaths(asList(PATH1, PATH2, PATH3));

        verify(children, times(3)).add(imageView);
    }

    @Test
    public void shouldSetImagesHeight() throws Exception {
        int height = 10;

        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(true).when(fileUtils).fileExists(PATH3);

        grid.setImagesHeight(height);
        grid.setPaths(asList(PATH1, PATH2, PATH3));

        verify(fitHeightProperty, times(3)).bind(imagesHeight);
    }
}
