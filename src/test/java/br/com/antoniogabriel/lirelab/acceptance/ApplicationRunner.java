package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.App;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionController;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.app.WelcomeController.CREATE_COLLECTION;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ApplicationRunner extends FxRobot {

    void setUpApp() throws TimeoutException {
        targetWindow(FxToolkit.registerPrimaryStage());
        FxToolkit.setupApplication(App.class);
        interrupt();
    }

    void tearDownApp() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public void openCreateCollectionDialog() {
        clickOn("#welcome-create-collection-button")
                .interrupt()
                .targetWindow(window(CREATE_COLLECTION));
    }


    public void fillCreateCollectionDialog(String name, String path, Feature[] features) {
        clickOn("#collection-name").write(name);
        clickOn("#images-directory").write(path);

        markCheckBoxFor(features);

        clickOn("#create");
    }

    public void markCheckBoxFor(Feature[] features) {
        for (Feature feature : features) {
            Node checkBox = lookup("#featuresTable")
                    .lookup(".table-row-cell").nth(feature.ordinal())
                    .lookup(".table-cell").nth(0)
                    .lookup(".check-box").query();
            clickOn(checkBox).interrupt();
        }
    }

    public void checkCreateCollectionProgressDialog() {
        targetWindow(window(CreateCollectionController.CREATING_COLLECTION));
    }

    public void checkCollectionIsListed(String collectionName) {
        throw new UnsupportedOperationException();
    }

    public void checkCollectionIsCreated(String collectionName) {
        throw new UnsupportedOperationException();
    }
}
