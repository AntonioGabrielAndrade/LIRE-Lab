package br.com.antoniogabriel.lirelab.acceptance.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class FXMLLoadOwnedByStageAcceptanceTest extends ApplicationTest {

    private static FXMLLoader loader = new FXMLLoader();

    private FXMLImplForTests fxml;
    private Stage owner;

    @Override
    public void start(Stage stage) throws Exception {
        owner = stage;

        owner.setScene(new Scene(getOwnerRoot(), 600, 400));
        owner.show();

        fxml = new FXMLImplForTests(loader);
        fxml.loadOwnedBy(owner);
    }

    private Parent getOwnerRoot() {
        Parent ownerRoot = new StackPane();
        ownerRoot.setStyle("-fx-background-color: khaki");
        return ownerRoot;
    }

    @Test
    public void shouldLoadFXMLInOwnedStage() throws Exception {
        verifyThat("#fxml-impl-root", isVisible());
    }

    @Test
    public void shouldCloseStageWhenOwnerClose() throws Exception {
        Node root = lookup("#fxml-impl-root").query();
        assertNotNull(root);

        interact(() -> owner.close());

        root = lookup("#fxml-impl-root").query();
        assertNull(root);
    }

    @Test
    public void shouldReturnController() throws Exception {
        FxmlImplForTestsController controller = fxml.getController();

        assertNotNull(controller);
    }
}
