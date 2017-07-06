package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.scene.Node;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test.AsyncUtils.waitUntilIsVisible;
import static org.testfx.api.FxAssert.verifyThat;

public class SearchViewObject extends FxRobot {

    public void checkImagesAreVisible(String... images) {
        new CollectionGridViewObject().checkImagesAreVisible(images);
    }

    public void selectImage(String image) {
        new CollectionGridViewObject().selectImage(image);
    }

    public void checkQueryIs(String image) {
        Node query = from(lookup("#query-pane")).lookup("#" + image).query();
        verifyThat(query, Node::isVisible);
    }

    public void checkImagesAreSortedFor(String image) {
        throw new UnsupportedOperationException();
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

    public void waitUntilImagesOrderIs() {
        throw new UnsupportedOperationException();
    }
}
