package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.collections.ObservableList;
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

    private static final String INEXISTENT_PATH = "INEXISTENT_PATH";
    public static final String PATH1 = "PATH1";
    public static final String PATH2 = "PATH2";
    public static final String PATH3 = "PATH3";

    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private FileUtils fileUtils;
    @Mock private ObservableList<Node> children;
    @Mock private FlowPane flowPane;

    @InjectMocks private ImageGrid grid = new ImageGrid(imageViewFactory, fileUtils);

    @Before
    public void setUp() throws Exception {
        doReturn(children).when(flowPane).getChildren();
        doReturn(imageView).when(imageViewFactory).create(anyString()) ;
    }

    @Test
    public void shouldNotAddImageWhenPathDontExist() throws Exception {
        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(false).when(fileUtils).fileExists(INEXISTENT_PATH);

        grid.setImages(asList(PATH1, PATH2, INEXISTENT_PATH));

        verify(children, times(2)).add(imageView);
    }

    @Test
    public void shouldAddImagesWhenPathsExist() throws Exception {
        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(true).when(fileUtils).fileExists(PATH3);

        grid.setImages(asList(PATH1, PATH2, PATH3));

        verify(children, times(3)).add(imageView);
    }

    @Test
    public void shouldSetImagesHeight() throws Exception {
        int height = 10;

        doReturn(true).when(fileUtils).fileExists(PATH1);
        doReturn(true).when(fileUtils).fileExists(PATH2);
        doReturn(true).when(fileUtils).fileExists(PATH3);

        grid.setImagesHeight(height);
        grid.setImages(asList(PATH1, PATH2, PATH3));

        verify(imageView, times(3)).setFitHeight(height);
    }
}
