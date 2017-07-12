package br.com.antoniogabriel.lirelab.acceptance.view;

import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class AppViewObject extends FxRobot {

    private static final String ABOUT_ROOT = "#about-root";

    private MenuViewObject menuView = new MenuViewObject();
    private ToolBarViewObject toolBarView = new ToolBarViewObject();
    private WelcomeViewObject welcomeView = new WelcomeViewObject();

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
        clickOn("#toolbar-create-collection").interrupt();

        return new CreateCollectionViewObject();
    }

    public void checkCollectionIsListed(String collectionName) throws TimeoutException {
        verifyThat("#collection-tree", isVisible());
        waitFor(isPresent(collectionName));
    }

    private void waitFor(Callable<Boolean> present) throws TimeoutException {
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, present);
    }

    private Callable<Boolean> isPresent(String text) {
        return () -> lookup(text).tryQuery().isPresent();
    }

    public SearchViewObject search() {
        clickOn("#toolbar-search-collection");
        return new SearchViewObject();
    }

    public AppViewObject openAboutDialog() {
        clickOn("#toolbar-about");
        return this;
    }

    public void checkAboutDialog() throws TimeoutException {
        waitUntilIsVisible("#splash-image", ABOUT_ROOT);
        waitUntilIsVisible("#about-info", ABOUT_ROOT);
    }
}
