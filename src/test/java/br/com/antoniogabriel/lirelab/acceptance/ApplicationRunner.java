package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.Feature;
import br.com.antoniogabriel.lirelab.Main;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.WelcomeViewController.CREATE_COLLECTION;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ApplicationRunner extends FxRobot {

    void setUpApp() throws TimeoutException {
        targetWindow(FxToolkit.registerPrimaryStage());
        FxToolkit.setupApplication(Main.class);
        interrupt();
    }

    void tearDownApp() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    void checkMenus() {
        checkMenuBar();
        checkFileMenu();
        checkHelpMenu();
    }

    private void checkMenuBar() {
        verifyThat("#menu-bar", isVisible());
        verifyThat("#menu-bar", hasChild("#file-menu"));
        verifyThat("#menu-bar", hasChild("#help-menu"));
    }

    private void checkHelpMenu() {
        clickOn("#help-menu");
        verifyThat("#about", isVisible());
        clickOn("#help-menu");
    }

    private void checkFileMenu() {
        clickOn("#file-menu");
        verifyThat("#create-collection", isVisible());
        clickOn("#file-menu");
    }

    void checkToolBar() {
        verifyThat("#toolbar", isVisible());
        verifyThat("#toolbar-create-collection", isVisible());
        verifyThat("#toolbar-about", isVisible());
    }

    void checkWelcomeView() {
        verifyThat("#welcome-view", isVisible());
        verifyThat("#welcome-title", isVisible());
        verifyThat("#welcome-text", isVisible());
        verifyThat("#welcome-create-collection-button", isVisible());
    }

    public void openCreateCollectionDialog() {
        clickOn("#welcome-create-collection-button")
                .interrupt()
                .targetWindow(window(CREATE_COLLECTION));
    }


    public void fillCreateCollectionDialog(String name, String path, Feature[] features) {
        clickOn("#collection-name").write(name);
        clickOn("#path-to-images").write(path);

        for (Feature feature : features) {
            Node checkBox = lookup("#featuresTable")
                    .lookup(".table-row-cell").nth(feature.ordinal())
                    .lookup(".table-cell").nth(0)
                    .lookup(".check-box").query();
            clickOn(checkBox).interrupt();
        }

        clickOn("#ok");
    }

    public void checkCreateCollectionProgressDialog() {
        throw new UnsupportedOperationException();
    }

    public void checkCollectionIsListed(String collectionName) {
        throw new UnsupportedOperationException();
    }

    public void checkCollectionIsCreated(String collectionName) {
        throw new UnsupportedOperationException();
    }
}
