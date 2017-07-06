package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.image_dialog.ImageDialog;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.test.TestPaths.TEST_IMAGES;

public class ImageDialogAcceptanceTest extends ApplicationTest {

    private ImageDialog dialog;
    private ImageDialogViewObject view = new ImageDialogViewObject();

    @Override
    public void start(Stage stage) throws Exception {}

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
        view.checkImageIsDisplayed("14474347006_99aa0fd981_k");
    }

    @Test
    public void shouldShowOkButton() throws Exception {
        view.close();
    }
}
