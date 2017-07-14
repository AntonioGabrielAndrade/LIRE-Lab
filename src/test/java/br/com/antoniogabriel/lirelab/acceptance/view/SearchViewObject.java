package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.custom.CollectionGridViewObject;
import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.*;

public class SearchViewObject extends FxRobot {

    public void checkImagesAreVisible(String... images) {
        new CollectionGridViewObject().checkImagesAreVisible(images);
    }

    public void waitUntilShowCollection(Collection collection) throws TimeoutException {
        for (Image image : collection.getImages()) {
            waitUntilIsVisible("#" + image.getImageName());
        }
    }

    public void selectQuery(String image) {
        clickOn("#" + image).interrupt();
    }

    public void waitUntilShowQuery(String image) throws TimeoutException {
        waitUntilIsVisible("#" + image, "#query");
    }

    public void waitUntilImagesAreOrderedLike(String... images) throws TimeoutException {
        waitUntilElementsAreOrderedLike("#output", ".image-view", images);
    }

    public void writeQueryPath(String path) {
        clickOn("#query-image-field").write("").interrupt().write(path);
    }

    public void checkRunIsEnabled() throws TimeoutException {
        waitUntil(() -> !lookup("#run-loaded-image").query().isDisabled());
    }

    public void checkRunIsDisabled() throws TimeoutException {
        waitUntil(() -> lookup("#run-loaded-image").query().isDisabled());
    }

    public void run() throws TimeoutException {
        checkRunIsEnabled();
        clickOn("#run-loaded-image").interrupt();
    }
}
