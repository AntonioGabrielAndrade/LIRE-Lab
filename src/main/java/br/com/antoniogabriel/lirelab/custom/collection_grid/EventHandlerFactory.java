package br.com.antoniogabriel.lirelab.custom.collection_grid;

import br.com.antoniogabriel.lirelab.collection.Image;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


class EventHandlerFactory {

    public EventHandler<MouseEvent> createFrom(Image image, ImageClickHandler handler) {
        return new ImageClickHandlerWrapper(image, handler);
    }

    class ImageClickHandlerWrapper implements EventHandler<MouseEvent> {
        private Image image;
        private ImageClickHandler handler;

        public ImageClickHandlerWrapper(Image image, ImageClickHandler handler) {
            this.image = image;
            this.handler = handler;
        }

        @Override
        public void handle(MouseEvent event) {
            handler.handle(image, event);
        }
    }
}
