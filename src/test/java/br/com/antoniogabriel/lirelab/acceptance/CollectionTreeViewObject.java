package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.collection.Collection;
import javafx.scene.Node;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test.AsyncUtils.waitUntilIsNotVisible;
import static br.com.antoniogabriel.lirelab.test.AsyncUtils.waitUntilIsVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class CollectionTreeViewObject extends FxRobot {

    public void checkCollectionsAreListed(Collection... collections) {
        for (Collection collection : collections) {
            verifyThat(collection.getName(), isVisible());
        }
    }

    public void waitUntilCollectionsAreListed(Collection... collections) throws TimeoutException {
        for (Collection collection : collections) {
            waitUntilCollectionIsListed(collection);
        }
    }

    public void waitUntilCollectionIsListed(Collection collection) throws TimeoutException {
        waitUntilIsVisible(collection.getName());
    }

    public void waitUntilCollectionIsNotListed(Collection collection) throws TimeoutException {
        waitUntilIsNotVisible(collection.getName());
    }

    public void expandCollection(Collection collection) throws TimeoutException {
        Node arrow = null;

        //hack for when the robot cant find the arrow at first
        while(arrow == null) {
            arrow = lookup(collection.getName()).lookup(".arrow").query();
        }

        clickOn(arrow).interrupt();
    }

    public void waitUntilImageIsListed(String image) throws TimeoutException {
        waitUntilIsVisible(image);
    }

    public void waitUntilImageIsVisible(String imageName) throws TimeoutException {
        waitUntilIsVisible("#" + imageName);
    }

    public void selectCollection(Collection collection) {
        clickOn(collection.getName()).interrupt();
    }

    public void selectCollection(String collection) {
        clickOn(collection).interrupt();
    }

    public void selectImage(String image) {
        clickOn(image).interrupt();
    }
}
