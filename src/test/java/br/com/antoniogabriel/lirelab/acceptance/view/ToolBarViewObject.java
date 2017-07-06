package br.com.antoniogabriel.lirelab.acceptance.view;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ToolBarViewObject extends FxRobot {

    public void checkStructure() {
        verifyThat("#toolbar", isVisible());
        verifyThat("#toolbar-create-collection", isVisible());
        verifyThat("#toolbar-search-collection", isVisible());
        verifyThat("#toolbar-about", isVisible());
    }
}
