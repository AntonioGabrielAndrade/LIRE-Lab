package br.com.antoniogabriel.lirelab.test_utilities;

import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import org.testfx.api.FxRobot;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testfx.util.WaitForAsyncUtils.waitFor;

public class AsyncUtils {

    private static final TimeOut DEFAULT_TIMEOUT = TimeOut.of(5, SECONDS);

    public static void waitUntil(ObservableBooleanValue condition) throws TimeoutException {
        waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public static void waitUntil(ObservableBooleanValue condition, TimeOut timeout) throws TimeoutException {
        waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntil(Callable<Boolean> condition) throws TimeoutException {
        waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public static void waitUntil(Callable<Boolean> condition, TimeOut timeout) throws TimeoutException {
        waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntilIsVisible(String query) throws TimeoutException {
        waitUntilVisibilityIs(query, true);
    }

    public static void waitUntilIsVisible(String query, String queryFrom) throws TimeoutException {
        waitUntilVisibilityIs(query, queryFrom, true);
    }

    public static void waitUntilIsNotVisible(String query, String queryFrom) throws TimeoutException {
        waitUntilVisibilityIs(query, queryFrom, false);
    }

    public static void waitUntilIsNotVisible(String query) throws TimeoutException {
        waitUntilVisibilityIs(query, false);
    }

    public static void waitUntilVisibilityIs(String query, String queryFrom, boolean visibility) throws TimeoutException {
        waitUntil(() -> {
            FxRobot robot = new FxRobot();
            Node node = robot.from(robot.lookup(queryFrom)).lookup(query).query();
            return (node == null) ? (!visibility) : (node.isVisible() == visibility);
        });
    }

    public static void waitUntilVisibilityIs(String query, boolean visibility) throws TimeoutException {
        waitUntil(() -> {
            FxRobot robot = new FxRobot();
            Node node = robot.lookup(query).query();
            return (node == null) ? (!visibility) : (node.isVisible() == visibility);
        });
    }
}
