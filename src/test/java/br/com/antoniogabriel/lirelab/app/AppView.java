package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

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
        toolBarView.checkToolBarStructure();
    }

    public void checkWelcomeView() {
        welcomeView.checkStructure();
    }
}
