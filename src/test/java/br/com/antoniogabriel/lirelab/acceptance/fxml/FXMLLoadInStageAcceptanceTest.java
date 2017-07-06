package br.com.antoniogabriel.lirelab.acceptance.fxml;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class FXMLLoadInStageAcceptanceTest extends ApplicationTest {

    private static FXMLLoader loader = new FXMLLoader();

    private FXMLImplForTests fxml;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        fxml = new FXMLImplForTests(loader);
        fxml.loadIn(stage);
    }

    @Test
    public void shouldLoadFXMLInStage() throws Exception {
        verifyThat("#fxml-impl-root", isVisible());
    }

    @Test
    public void shouldReturnController() throws Exception {
        FxmlImplForTestsController controller = fxml.getController();

        assertNotNull(controller);
    }

    @Test
    public void shouldLoadWithSameLoaderInstanceSeveralTimesInARow() throws Exception {
        closeAndLoadAgain();
        verifyThat("#fxml-impl-root", isVisible());

        closeAndLoadAgain();
        verifyThat("#fxml-impl-root", isVisible());
    }

    private void closeAndLoadAgain() {
        interact(() -> {
            stage.close();
            fxml.loadIn(stage);
        });
    }
}
