package br.com.antoniogabriel.lirelab.test;

import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AsyncUtils {

    private static final TimeOut DEFAULT_TIMEOUT = TimeOut.of(5, SECONDS);

    public static void waitUntil(ObservableBooleanValue condition) throws TimeoutException {
        waitUntil(condition, TimeOut.of(5, SECONDS));
    }

    public static void waitUntil(ObservableBooleanValue condition, TimeOut timeout) throws TimeoutException {
        WaitForAsyncUtils.waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntil(Callable<Boolean> condition) throws TimeoutException {
        waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public static void waitUntil(Callable<Boolean> condition, TimeOut timeout) throws TimeoutException {
        WaitForAsyncUtils.waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntilIsVisible(String query) throws TimeoutException {
        waitUntil(() -> {
            Node node = (new FxRobot()).lookup(query).query();
            return node == null ? false : node.isVisible();
        });
    }

    public static void waitUntilIsVisible(String query, String queryFrom) throws TimeoutException {
        waitUntil(() -> {
            FxRobot robot = new FxRobot();
            Node node = robot.from(robot.lookup(queryFrom)).lookup(query).query();
            return node == null ? false : node.isVisible();
        });
    }

    public static void waitUntilIsNotVisible(String query) throws TimeoutException {
        waitUntil(() -> {
            Node node = (new FxRobot()).lookup(query).query();
            return node == null ? true : !node.isVisible();
        });
    }
}
