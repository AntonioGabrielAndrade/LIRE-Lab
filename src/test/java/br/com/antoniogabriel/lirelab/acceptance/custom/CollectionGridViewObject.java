package br.com.antoniogabriel.lirelab.acceptance.custom;

import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class CollectionGridViewObject extends FxRobot {

    public void checkImagesAreVisible(String... images) {
        for (String image : images) {
            verifyThat("#" + image, isVisible());
        }
    }

    public ImageDialogViewObject selectImage(String image) {
        clickOn("#" + image).interrupt();

        return new ImageDialogViewObject();
    }
}
