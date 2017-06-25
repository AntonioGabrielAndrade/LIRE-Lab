package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.app.MainAreaController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListCollectionControllerTest {

    public static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private ObservableList rootChildren;
    @Mock private CollectionService service;
    @Mock private TreeItem<String> rootItem;
    @Mock private MainAreaController appController;
    @Mock private TreeView<String> collectionsTree;
    @Mock private TreeItem<String> collectionItem;
    @Mock private MultipleSelectionModel<TreeItem<String>> treeSelectionModel;
    @Mock private ReadOnlyObjectProperty<TreeItem<String>> selectedItemProperty;
    @Mock private ChangeListener<TreeItem<String>> treeItemChangeListener;
    @Mock private Runnable collectionsChangeListener;

    private List<Collection> collections;

    @InjectMocks ListCollectionController controller = new TestableListCollectionController();


    @Before
    public void setUp() throws Exception {
        collections = Arrays.asList(
                new Collection("Collection1"),
                new Collection("Collection2"),
                new Collection("Collection3")
        );

        given(collectionsTree.getSelectionModel()).willReturn(treeSelectionModel);
        given(treeSelectionModel.selectedItemProperty()).willReturn(selectedItemProperty);
        given(service.getCollections()).willReturn(collections);
        given(rootItem.getChildren()).willReturn(rootChildren);
    }

    @Test
    public void shouldAddCollectionsToTreeWhenInitialize() throws Exception {

        controller.initialize(null, null);

        verify(rootChildren, times(collections.size())).add(collectionItem);
        verify(collectionsTree).setRoot(rootItem);
    }

    @Test
    public void shouldListenToCollectionSelectionWhenInitialize() throws Exception {

        controller.initialize(null, null);

        verify(selectedItemProperty).addListener(treeItemChangeListener);
    }

    @Test
    public void shouldListenToCollectionsChangeWhenInitialize() throws Exception {
        controller.initialize(null, null);

        verify(service).addCollectionsChangeListener(collectionsChangeListener);
    }

    private class TestableListCollectionController extends ListCollectionController {

        public TestableListCollectionController() {
            super(service, appController);
        }

        @Override
        protected TreeItem createRootItem() {
            return rootItem;
        }

        @Override
        protected TreeItem<String> createCollectionItem(Collection collection) {
            return collectionItem;
        }

        @Override
        protected ChangeListener<TreeItem<String>> getTreeItemChangeListener() {
            return treeItemChangeListener;
        }

        @Override
        protected Runnable getCollectionsChangeListener() {
            return collectionsChangeListener;
        }
    }
}