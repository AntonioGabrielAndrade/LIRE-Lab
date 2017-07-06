package br.com.antoniogabriel.lirelab.acceptance.custom;

import javafx.scene.Node;
import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ImageDialogViewObject extends FxRobot {

    public void checkImageIsDisplayed(String imageName) {
        Node imageNode = from(lookup("#image-dialog")).lookup("#" + imageName).query();
        verifyThat(imageNode, isVisible());
    }

    public void close() {
        clickOn("OK").interrupt();
    }
}
