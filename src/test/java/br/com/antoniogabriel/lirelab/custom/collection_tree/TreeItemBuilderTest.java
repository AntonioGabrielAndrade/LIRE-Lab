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

package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TreeItem;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TreeItemBuilderTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private TreeItemBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new TreeItemBuilder();
    }

    @Test
    public void shouldCreateCollectionItemWithNameAsValue() throws Exception {
        Collection collection = new Collection("Collection");

        TreeItem<String> item = builder.createItem(collection);

        assertThat(item.getValue(), equalTo("Collection"));
    }

    @Test
    public void shouldCreateImageItemWithFilenameAsValue() throws Exception {
        TreeItem<String> item = builder.createItem("path/to/some_image");

        assertThat(item.getValue(), equalTo("some_image"));
    }
}