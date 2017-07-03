package br.com.antoniogabriel.lirelab.custom.collectiongrid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.scene.input.MouseEvent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventHandlerFactoryTest {

    private static final String SOME_PATH = "some/fake/path";

    private boolean wrappedHandlerExecuted = false;
    private EventHandlerFactory factory = new EventHandlerFactory();
    private Image image = new Image(SOME_PATH, SOME_PATH);
    private MouseEvent event = null;

    @Test
    public void shouldWrapImageClickHandler() throws Exception {
        ImageClickHandler imageClickHandler = (image, event) -> wrappedHandlerExecuted = true;
        factory.createFrom(image, imageClickHandler).handle(event);

        assertTrue(wrappedHandlerExecuted);
    }
}