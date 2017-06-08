package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AppView extends FxRobot {

    MenuView menuView = new MenuView();
    ToolBarView toolBarView = new ToolBarView();

    public void checkMenus() {
        menuView.checkMenuBar();
        menuView.checkFileMenu();
        menuView.checkHelpMenu();
    }

    public void checkToolBar() {
        toolBarView.checkToolBarStructure();
    }

    public void checkWelcomeView() {
        verifyThat("#welcome-view", isVisible());
        verifyThat("#welcome-title", isVisible());
        verifyThat("#welcome-text", isVisible());
        verifyThat("#welcome-create-collection-button", isVisible());
    }
}
