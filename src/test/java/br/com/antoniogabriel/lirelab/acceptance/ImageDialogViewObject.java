package br.com.antoniogabriel.lirelab.acceptance;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ImageDialogViewObject extends FxRobot {

    public void checkImageIsDisplayed(String image) {
        verifyThat("#" + image + "-dialog", isVisible());
    }

    public void close() {
        clickOn("Close").interrupt();
    }
}
