package br.com.antoniogabriel.lirelab.acceptance.custom;

import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsNotVisible;
import static br.com.antoniogabriel.lirelab.test_utilities.AsyncUtils.waitUntilIsVisible;

public class StatusBarViewObject extends FxRobot {

    public void waitUntilStatusMessageIs(String message) throws TimeoutException, InterruptedException {
        waitUntilIsVisible(message, "#status-root");
    }

    public void checkProgressBarIsNotVisible() throws TimeoutException {
        waitUntilProgressBarIsNotVisible();
    }

    public void waitUntilProgressBarIsVisible() throws TimeoutException {
        waitUntilIsVisible("#status-progress");
    }

    public void waitUntilProgressBarIsNotVisible() throws TimeoutException {
        waitUntilIsNotVisible("#status-progress");
    }
}
