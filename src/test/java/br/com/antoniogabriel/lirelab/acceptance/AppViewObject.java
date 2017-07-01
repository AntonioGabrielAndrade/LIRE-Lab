package br.com.antoniogabriel.lirelab.acceptance;

import org.jetbrains.annotations.NotNull;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class AppViewObject extends FxRobot {

    MenuViewObject menuView = new MenuViewObject();
    ToolBarViewObject toolBarView = new ToolBarViewObject();
    WelcomeViewObject welcomeView = new WelcomeViewObject();

    public void checkMenus() {
        menuView.checkMenuBar();
        menuView.checkFileMenu();
        menuView.checkHelpMenu();
    }

    public void checkToolBar() {
        toolBarView.checkStructure();
    }

    public void checkWelcomeView() {
        welcomeView.checkStructure();
    }

    public CreateCollectionViewObject createCollection() {
        clickOn("#toolbar-create-collection")
                .interrupt();

        return new CreateCollectionViewObject();
    }

    public void checkCollectionIsListed(String collectionName) throws TimeoutException {
        verifyThat("#collection-tree", isVisible());
        waitFor(isPresent(collectionName));

    }

    private void waitFor(Callable<Boolean> present) throws TimeoutException {
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS,
                present);
    }

    @NotNull
    private Callable<Boolean> isPresent(String text) {
        return () -> lookup(text).tryQuery().isPresent();
    }
}
