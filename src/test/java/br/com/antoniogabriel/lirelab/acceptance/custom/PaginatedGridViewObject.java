package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.collection.Image;
import org.testfx.api.FxRobot;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class PaginatedGridViewObject extends FxRobot{

    public void checkImagesAreVisible(Image... images) {
        for (Image image : images) {
            verifyThat("#" + image.getImageName(), isVisible());
        }
    }

    public void nextPage() {
        clickOn(".right-arrow").interrupt();
    }
}