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

package net.lirelab.custom.collection_tree;

import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TreeItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CollectionTreeTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private TreeItem<String> rootItem;
    @Mock private ObservableList rootChildren;
    @Mock private TreeItemBuilder itemBuilder;
    @Mock private TreeItem<String> collectionItem;
    @Mock private ObservableList collectionChildren;
    @Mock private TreeItem<String> imageItem;

    @InjectMocks private CollectionTree tree = new CollectionTree();

    private Collection collection1 = new Collection("Collection1");
    private Collection collection2 = new Collection("Collection2");
    private Collection collection3 = new Collection("Collection3");

    private List<Collection> collections;

    @Before
    public void setUp() throws Exception {
        tree.setItemBuilder(itemBuilder);
        setupCollections();
        setupMocks();
    }

    protected void setupMocks() throws Exception {
        given(itemBuilder.createItem(any(Collection.class))).willReturn(collectionItem);
        given(rootItem.getChildren()).willReturn(rootChildren);
        given(itemBuilder.createItem(anyString())).willReturn(imageItem);
        given(collectionItem.getChildren()).willReturn(collectionChildren);
    }

    protected void setupCollections() {
        collection1.setImages(asList(new Image("path/image1", "path/image1"), new Image("path/image2", "path/image2")));
        collection2.setImages(asList(new Image("path/image3", "path/image3"), new Image("path/image4", "path/image4")));
        collection3.setImages(asList(new Image("path/image5", "path/image5"), new Image("path/image6", "path/image6")));

        collections = asList(collection1, collection2, collection3);
    }

    @Test
    public void shouldAddCollectionsToTree() throws Exception {
        tree.setCollections(collections);

        verify(rootChildren).clear();
        verify(rootChildren, times(3)).add(collectionItem);
    }

    @Test
    public void shouldAddImagesToCollection() throws Exception {
        tree.setCollections(collections);

        verify(collectionChildren, times(6)).add(imageItem);
    }

    @Test
    public void shouldReturnSelectedCollection() throws Exception {
        CollectionTree realTree = new CollectionTree();

        realTree.setCollections(asList(collection1, collection2));

        realTree.selectCollection(0);

        assertThat(realTree.getSelectedCollection(), equalTo(collection1));
    }
}
