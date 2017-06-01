package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.Feature;
import br.com.antoniogabriel.lirelab.acceptance.ApplicationRunner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static br.com.antoniogabriel.lirelab.WelcomeViewController.CREATE_COLLECTION;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.WindowMatchers.isNotShowing;

public class CreateCollectionControllerTest extends ApplicationTest {

    private ApplicationRunner runner = new ApplicationRunner();
    private Feature[] featuresForTest = {Feature.CEDD, Feature.TAMURA};

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("create-collection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(CREATE_COLLECTION);
        stage.centerOnScreen();
        stage.show();
    }

    @Test
    public void shouldCloseOnCancel() throws Exception {
        Window window = window(CREATE_COLLECTION);
        clickOn("#cancel");
        verifyThat(window, isNotShowing());
    }

    @Test
    public void shouldDisableCreateButtonIfNameFieldIsEmpty() throws Exception {
        clickOn("#collection-name").write("");
        clickOn("#path-to-images").write("some/test/path");

        runner.markCheckBoxFor(featuresForTest);

        verifyThat("#create", isDisabled());
    }

    @Test
    public void shouldDisableCreateButtonIfPathFieldIsEmpty() throws Exception {
        clickOn("#collection-name").write("Some Name");
        clickOn("#path-to-images").write("");

        runner.markCheckBoxFor(featuresForTest);

        verifyThat("#create", isDisabled());
    }
}
