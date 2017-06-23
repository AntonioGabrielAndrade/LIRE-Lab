package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    @Mock BorderPane centerPane;
    @Mock FlowPane flowPane;
    @Mock ImageView imageView;
    @Mock ObservableList<Node> flowPaneChildren;

    private MainAreaController controller;
    private Collection collection;
    private List<String> paths;
    private boolean filesExists;

    @Before
    public void setUp() throws Exception {
        controller = new TestableMainAreaController();
        paths = asList("path1", "path2", "path3");
        collection = new Collection();
        collection.setImagePaths(paths);

        given(flowPane.getChildren()).willReturn(flowPaneChildren);
    }

    @Test
    public void shouldAddCollectionImagesToUI() throws Exception {
        filesExists = true;
        controller.showCollectionImages(collection);

        verify(flowPaneChildren, times(paths.size())).add(imageView);
        verify(centerPane).setCenter(flowPane);
    }

    @Test
    public void shouldNotAddImageIfFileNotExist() throws Exception {
        filesExists = false;
        controller.showCollectionImages(collection);

        verify(flowPaneChildren, never()).add(imageView);
    }

    private class TestableMainAreaController extends MainAreaController {
        @Override
        protected @NotNull ImageView getImageView(String thumb) throws FileNotFoundException {
            return imageView;
        }

        @Override
        protected boolean fileExists(String thumb) {
            return filesExists;
        }

        @Override
        protected List<String> getAllImages(Collection collection) throws IOException {
            return paths;
        }

        @Override
        protected @NotNull FlowPane getFlowPane() {
            return flowPane;
        }

        @Override
        protected BorderPane getCenterPane() {
            return centerPane;
        }
    }
}