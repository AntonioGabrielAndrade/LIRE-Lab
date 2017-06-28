package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
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
        collection1.setImagePaths(asList("path/image1", "path/image2"));
        collection2.setImagePaths(asList("path/image3", "path/image4"));
        collection3.setImagePaths(asList("path/image5", "path/image6"));
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
    public void shouldAddListenerForCollectionSelection() throws Exception {
        CollectionTree realTree = new CollectionTree();

        realTree.setCollections(asList(collection1, collection2));

        realTree.addCollectionSelectionListener(collection -> {
             collection.setName("was selected");
        });

        realTree.selectCollection(0);

        assertThat(collection1.getName(), equalTo("was selected"));
    }
}
