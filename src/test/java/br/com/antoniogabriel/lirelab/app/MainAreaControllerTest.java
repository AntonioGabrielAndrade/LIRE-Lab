package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.LireLabException;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainAreaControllerTest {

    private static final String DONT_EXIST = "DONT EXIST";
    private static final String THROW_EXCEPTION = "THROW EXCEPTION";

    @Mock BorderPane centerPane;
    @Mock FlowPane flowPane;
    @Mock ImageView imageView;
    @Mock ObservableList<Node> flowPaneChildren;

    private MainAreaController controller;
    private Collection collection;
    private List<String> paths;

    @Before
    public void setUp() throws Exception {
        controller = new TestableMainAreaController();
        paths = new ArrayList<>(asList("path1", "path2", "path3"));
        collection = new Collection();
        collection.setImagePaths(paths);

        given(flowPane.getChildren()).willReturn(flowPaneChildren);
    }

    @Test
    public void shouldAddCollectionImagesToCenter() throws Exception {
        controller.showCollectionImages(collection);

        verify(flowPaneChildren, times(paths.size())).add(imageView);
        verify(centerPane).setCenter(flowPane);
    }

    @Test
    public void shouldNotAddImageIfFileNotExist() throws Exception {
        paths.add(DONT_EXIST);
        controller.showCollectionImages(collection);

        verify(flowPaneChildren, times(paths.size()-1))
                .add(imageView);
    }

    @Test(expected = LireLabException.class)
    public void shouldThrowCustomExceptionIfIOExceptionOccurs() throws Exception {
        collection.setName(THROW_EXCEPTION);
        controller.showCollectionImages(collection);
    }

    private class TestableMainAreaController extends MainAreaController {
        @Override
        protected @NotNull ImageView createImageView(String thumb) {
            return imageView;
        }

        @Override
        protected boolean fileExists(String thumb) {
            return !thumb.equals(DONT_EXIST);
        }

        @Override
        protected List<String> getThumbnailsPaths(Collection collection) throws IOException {
            if(collection.getName().equals(THROW_EXCEPTION))
                throw new IOException();
            return paths;
        }

        @Override
        protected @NotNull FlowPane createFlowPane() {
            return flowPane;
        }

        @Override
        protected BorderPane getCenterPane() {
            return centerPane;
        }
    }
}