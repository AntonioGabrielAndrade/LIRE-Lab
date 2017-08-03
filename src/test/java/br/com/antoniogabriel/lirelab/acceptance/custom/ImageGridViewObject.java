package br.com.antoniogabriel.lirelab.acceptance.custom;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ImageGridViewObject {

    public void checkImagesAreVisible(String... images) {
        for (String image : images) {
            checkImageIsVisible(image);
        }
    }

    public void checkImageIsVisible(String image) {
        verifyThat("#" + image, isVisible());
    }

    public void waitUntilImageIsVisible(String image) {
        verifyThat("#" + image, isVisible());
    }
}
