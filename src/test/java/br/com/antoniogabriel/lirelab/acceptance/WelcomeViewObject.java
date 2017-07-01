package br.com.antoniogabriel.lirelab.acceptance;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class WelcomeViewObject extends FxRobot {

    public void checkStructure() {
        verifyThat("#welcome-view", isVisible());
        verifyThat("#welcome-title", isVisible());
        verifyThat("#welcome-text", isVisible());
        verifyThat("#welcome-create-collection-button", isVisible());
    }
}