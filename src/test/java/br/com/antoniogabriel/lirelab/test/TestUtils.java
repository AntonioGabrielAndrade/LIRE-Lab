package br.com.antoniogabriel.lirelab.test;

import javafx.embed.swing.JFXPanel;
import org.testfx.api.FxRobot;

public class TestUtils {

    public static void startJavaFX() {
        new JFXPanel();
    }

    public static void runOnFXThread(Runnable runnable) {
        new FxRobot().interact(runnable);
    }
}
