package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DisplayImageDialogHandlerTest {

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

    private DisplayImageDialogHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new DisplayImageDialogHandler(dialogProvider, fileUtils);
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(dialogProvider.getImageDialog(IMAGE_PATH, window)).willReturn(originalImageDialog);
        given(dialogProvider.getImageDialog(THUMBNAIL_PATH, window)).willReturn(thumbnailDialog);
    }

    @Test
    public void shouldShowImageInDialogWhenImagePathExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(true);

        handler.handle(IMAGE, event);

        verify(originalImageDialog).show();
    }

    @Test
    public void shouldShowThumbnailInDialogWhenImagePathDontExists() throws Exception {
        given(fileUtils.fileExists(IMAGE_PATH)).willReturn(false);

        handler.handle(IMAGE, event);

        verify(thumbnailDialog).show();
    }
}