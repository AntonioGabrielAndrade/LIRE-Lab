package br.com.antoniogabriel.lirelab.collection;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ListCollectionViewObject extends FxRobot {

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
        waitFor(isPresent(collection.getName()));
    }

    public void waitUntilCollectionIsNotListed(Collection collection) throws TimeoutException {
        waitFor(notPresent(collection.getName()));
    }

    private void waitFor(Callable<Boolean> condition) throws TimeoutException {
        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, condition);
    }

    public void expandCollection(Collection collection) throws TimeoutException {
        Node arrow = null;

        //hack for when the robot cant find the arrow at first
        while(arrow == null) {
            arrow = lookup(collection.getName()).lookup(".arrow").query();
        }

        clickOn(arrow).interrupt();
    }

    public void checkImageIsListed(String image) throws TimeoutException {
        waitFor(isPresent(image));
    }

    @NotNull
    private Callable<Boolean> isPresent(String text) {
        return () -> {
            Node node = ListCollectionViewObject.this.lookup(text).query();
            if(node == null)
                return false;
            else
                return node.isVisible();
        };
    }

    @NotNull
    private Callable<Boolean> notPresent(String text) {
        return () -> {
            Node node = ListCollectionViewObject.this.lookup(text).query();
            if(node == null)
                return true;
            else
                return !node.isVisible();
        };
    }

}
