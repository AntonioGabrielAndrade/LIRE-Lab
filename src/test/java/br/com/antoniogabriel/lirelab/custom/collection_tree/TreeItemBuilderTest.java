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