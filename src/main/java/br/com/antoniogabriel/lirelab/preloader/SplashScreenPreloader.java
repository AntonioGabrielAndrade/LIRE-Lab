package br.com.antoniogabriel.lirelab.preloader;

import br.com.antoniogabriel.lirelab.app.AboutFXML;
import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SplashScreenPreloader extends Preloader {

    private Stage stage;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);

        AboutFXML aboutFXML = new AboutFXML(new FXMLLoader());
        aboutFXML.loadIn(stage);
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            if (stage.isShowing()) {
                // fade out, hide stage at the end of animation
                final FadeTransition fadeTransition =
                        new FadeTransition(
                                Duration.millis(1000), stage.getScene().getRoot());
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                final EventHandler<ActionEvent> eventHandler = t -> stage.hide();
                fadeTransition.setOnFinished(eventHandler);
                fadeTransition.play();
            } else {
                stage.hide();
            }
        }
    }
}
