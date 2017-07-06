package br.com.antoniogabriel.lirelab.test;

import javafx.beans.value.ObservableBooleanValue;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AsyncUtils {

    public static void waitUntil(ObservableBooleanValue condition) throws TimeoutException {
        waitUntil(condition, TimeOut.of(5, SECONDS));
    }

    public static void waitUntil(ObservableBooleanValue condition, TimeOut timeout) throws TimeoutException {
        WaitForAsyncUtils.waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntilIsPresent(String query) throws TimeoutException {
        WaitForAsyncUtils.waitFor(5, SECONDS,
                () -> new FxRobot().lookup(query).tryQuery().isPresent());
    }

    public static void waitUntilIsPresent(String query, String queryFrom) throws TimeoutException {
        FxRobot robot = new FxRobot();
        WaitForAsyncUtils.waitFor(5, SECONDS,
                () -> robot.from(robot.lookup(queryFrom)).lookup(query).tryQuery().isPresent());
    }
}
