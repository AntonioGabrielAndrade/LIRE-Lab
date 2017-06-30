package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DisplayImageInDialogHandlerTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String IMAGE_PATH = "some/image/path";
    private static final String THUMBNAIL_PATH = "some/thumbnail/path";

    private static final Image IMAGE = new Image(IMAGE_PATH, THUMBNAIL_PATH);

    @Mock private ImageDialog originalImageDialog;
    @Mock private ImageDialog thumbnailDialog;
    @Mock private MouseEvent event;
    @Mock private DialogProvider dialogProvider;
    @Mock private Window window;
    @Mock private FileUtils fileUtils;

    @InjectMocks private DisplayImageDialogHandler handler
            = new DisplayImageDialogHandler(IMAGE, dialogProvider, fileUtils);

    @Before
    public void setUp() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(dialogProvider.getImageDialog(IMAGE_PATH, window)).willReturn(originalImageDialog);
        given(dialogProvider.getImageDialog(THUMBNAIL_PATH, window)).willReturn(thumbnailDialog);
    }

    @Test
    public void shouldShowImageInDialogWhenImagePathExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(true);

        handler.handle(event);

        verify(originalImageDialog).show();
    }

    @Test
    public void shouldShowThumbnailInDialogWhenImagePathDontExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(false);

        handler.handle(event);

        verify(thumbnailDialog).show();
    }
}