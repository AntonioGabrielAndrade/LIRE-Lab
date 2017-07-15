package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.custom.statusbar.StatusBar;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class StatusBarAcceptanceTest extends ApplicationTest {

    private StatusBar statusBar;
    private StatusBarViewObject view = new StatusBarViewObject();

    @Override
    public void start(Stage stage) throws Exception {
        statusBar = new StatusBar();
        Scene scene = new Scene(statusBar, 900, 30);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldSetSearchStatusInfo() throws Exception {
        Platform.runLater(() -> {
            statusBar.setSearchStatusInfo(new Collection("testCollection"), Feature.CEDD);
        });

        view.waitUntilStatusMessageIs("Collection: testCollection  Feature: CEDD");
    }

    @Test
    public void shouldShowProgressBarWhileGivenTaskRun() throws Exception {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };

        statusBar.bindProgressTo(task);
        view.checkProgressBarIsNotVisible();

        new Thread(task).start();

        view.waitUntilProgressBarIsVisible();
        view.waitUntilProgressBarIsNotVisible();
    }
}
