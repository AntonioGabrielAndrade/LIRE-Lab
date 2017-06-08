package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class MenuView extends FxRobot {

    public void checkMenuBar() {
        verifyThat("#menu-bar", isVisible());
        verifyThat("#menu-bar", hasChild("#file-menu"));
        verifyThat("#menu-bar", hasChild("#help-menu"));
    }

    public void checkFileMenu() {
        clickOn("#file-menu");
        verifyThat("#create-collection", isVisible());
        clickOn("#file-menu");
    }

    public void checkHelpMenu() {
        clickOn("#help-menu");
        verifyThat("#about", isVisible());
        clickOn("#help-menu");
    }

}
