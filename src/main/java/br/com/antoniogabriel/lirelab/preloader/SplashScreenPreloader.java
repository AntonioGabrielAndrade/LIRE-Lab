package br.com.antoniogabriel.lirelab.preloader;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SplashScreenPreloader extends Preloader {

    private Stage stage;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);

        Image splashImage = new Image(getClass().getResourceAsStream("splash-screen.jpg"));
        ImageView splashImageView = new ImageView(splashImage);
        StackPane root = new StackPane(splashImageView);
        stage.setScene(new Scene(root));
        stage.show();
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
