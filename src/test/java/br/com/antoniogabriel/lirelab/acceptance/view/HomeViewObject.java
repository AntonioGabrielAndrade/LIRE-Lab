package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.acceptance.custom.CollectionTreeViewObject;
import br.com.antoniogabriel.lirelab.collection.Collection;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class HomeViewObject extends FxRobot {

    private CollectionTreeViewObject treeViewObject = new CollectionTreeViewObject();


    public void waitUntilCollectionsAreListed(Collection... collections) throws TimeoutException {
        treeViewObject.waitUntilCollectionsAreListed(collections);
    }

    public void waitUntilCollectionIsListed(Collection collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsListed(collection);
    }

    public void waitUntilCollectionIsListed(String collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsListed(collection);
    }

    public void expandCollection(Collection collection) throws TimeoutException {
        treeViewObject.expandCollection(collection);
    }

    public void waitUntilImageIsListed(String imageFileName) throws TimeoutException {
        treeViewObject.waitUntilImageIsListed(imageFileName);
    }

    public void waitUntilCollectionIsNotListed(Collection collection) throws TimeoutException {
        treeViewObject.waitUntilCollectionIsNotListed(collection);
    }

    public void selectCollection(Collection collection) {
        treeViewObject.selectCollection(collection);
    }

    public void waitUntilImageIsVisible(String imageName) throws TimeoutException {
        waitUntilIsVisible("#" + imageName, "#center-pane");
    }

    public void selectImage(String imageName) {
        treeViewObject.selectImage(imageName);
    }

    public HomeViewObject deleteByContextMenu(Collection collection) throws TimeoutException {
        treeViewObject.openContextMenu(collection);
        treeViewObject.deleteCollection(collection);
        return this;
    }

    public void ok() {
        clickOn("OK").interrupt();
    }
}
