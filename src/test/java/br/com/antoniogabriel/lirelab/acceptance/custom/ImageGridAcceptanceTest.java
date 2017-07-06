package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.image_grid.ImageGrid;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.semanticmetadata.lire.utils.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ImageGridAcceptanceTest extends ApplicationTest {

    private ImageGrid imageGrid = new ImageGrid();
    private List<String> paths = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        paths = getPaths(TEST_IMAGES);

        imageGrid.setImagesHeight(100);
        imageGrid.setPaths(paths);

        Scene scene = new Scene(imageGrid, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowImages() throws Exception {
        verifyThat("#14474347006_99aa0fd981_k", isVisible());
        verifyThat("#16903390174_1d670a5849_h", isVisible());
        verifyThat("#17099294578_0ba4068bad_k", isVisible());
        verifyThat("#17338370170_1e620bfb18_h", isVisible());
        verifyThat("#17525978165_86dc26e8cb_h", isVisible());
        verifyThat("#19774866363_757555901c_k", isVisible());
        verifyThat("#25601366680_b57441bb52_k", isVisible());
        verifyThat("#25601374660_78e6a9bba8_k", isVisible());
        verifyThat("#26487616294_b22b87133e_k", isVisible());
        verifyThat("#26489383923_98d419eb0d_k", isVisible());
    }

    protected ArrayList<String> getPaths(String testImages) throws IOException {
        return FileUtils.getAllImages(new File(testImages), false);
    }
}
