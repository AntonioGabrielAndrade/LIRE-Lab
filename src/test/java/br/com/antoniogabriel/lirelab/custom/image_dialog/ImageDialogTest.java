package br.com.antoniogabriel.lirelab.custom.image_dialog;

import br.com.antoniogabriel.lirelab.app.ImageViewConfig;
import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog.MAX_IMAGE_HEIGHT;
import static br.com.antoniogabriel.lirelab.test_utilities.TestUtils.runOnFxThreadAndWait;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageDialogTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String IMAGE_PATH = "some/image/path";

    @Mock private ImageView imageView;
    @Mock private ImageViewFactory imageViewFactory;
    @Mock private DialogActions dialogActions;
    @Mock private ImageViewConfig imageViewConfig;
    @Mock private BorderPane contentRoot;

    private ImageDialog dialog;

    @Before
    public void setUp() throws Exception {
        given(imageViewFactory.create(IMAGE_PATH, false)).willReturn(imageView);
        createDialog();
    }

    @Test
    public void shouldSetImageAsContentWhenInitialize() throws Exception {
        verify(contentRoot).setCenter(imageView);
        verify(dialogActions).setContent(contentRoot);
    }

    @Test
    public void shouldAddOkButtonWhenInitialize() throws Exception {
        verify(dialogActions).addButtonType(ButtonType.OK);
    }

    @Test
    public void shouldLimitImageHeightToAMaximum() throws Exception {
        verify(imageViewConfig).limitImageHeight(imageView, MAX_IMAGE_HEIGHT);
    }

    @Test
    public void shouldSetDialogTitleAsImagePath() throws Exception {
        verify(dialogActions).setTitle(IMAGE_PATH);
    }

    @Test
    public void shouldSetDialogAsResizable() throws Exception {
        verify(dialogActions).setResizable(true);
    }

    private void createDialog() {
        runOnFxThreadAndWait(() -> dialog =
                new ImageDialog(IMAGE_PATH,
                                imageViewFactory,
                                imageViewConfig,
                                dialogActions,
                                contentRoot));
    }
}