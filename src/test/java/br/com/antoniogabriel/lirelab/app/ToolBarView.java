package br.com.antoniogabriel.lirelab.app;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ToolBarView extends FxRobot {

    public void checkTooBar() {
        verifyThat("#toolbar", isVisible());
        verifyThat("#toolbar-create-collection", isVisible());
        verifyThat("#toolbar-about", isVisible());
    }
}
