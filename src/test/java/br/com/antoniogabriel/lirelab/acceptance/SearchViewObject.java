package br.com.antoniogabriel.lirelab.acceptance;

import javafx.scene.Node;
import org.testfx.api.FxRobot;

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

}
