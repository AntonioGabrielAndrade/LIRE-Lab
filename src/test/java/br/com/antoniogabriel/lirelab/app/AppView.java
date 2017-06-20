package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionView;
import org.jetbrains.annotations.NotNull;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class AppView extends FxRobot {

    MenuView menuView = new MenuView();
    ToolBarView toolBarView = new ToolBarView();
    WelcomeView welcomeView = new WelcomeView();

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

    public CreateCollectionView createCollection() {
        clickOn("#welcome-create-collection-button")
                .interrupt();

        return new CreateCollectionView();
    }

    public void checkCollectionIsListed(String collectionName) throws TimeoutException {
        verifyThat("#collections-tree", isVisible());
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
