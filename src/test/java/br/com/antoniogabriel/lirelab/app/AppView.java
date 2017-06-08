package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AppView extends FxRobot {

    public void checkMenus() {
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
}
