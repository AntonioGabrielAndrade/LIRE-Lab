package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AppView extends FxRobot {

    MenuView menuView = new MenuView();

    public void checkMenus() {
        menuView.checkMenuBar();
        menuView.checkFileMenu();
        menuView.checkHelpMenu();
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
