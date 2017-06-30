package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.custom.ImageDialog;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ImageDialogAcceptanceTest extends ApplicationTest {

    private ImageDialog dialog;
    private ImageView imageView;

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        interact(() -> {
            dialog = new ImageDialog(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg");
            dialog.show();
        });
    }

    @After
    public void tearDown() throws Exception {
        interact(() -> {
            dialog.close();
        });
    }

    @Test
    public void shouldShowImage() throws Exception {
        Node imageNode = from(lookup("#image-dialog")).lookup("#14474347006_99aa0fd981_k").query();
        verifyThat(imageNode, isVisible());
    }

    @Test
    public void shouldShowOkButton() throws Exception {
        clickOn("OK");
    }
}
