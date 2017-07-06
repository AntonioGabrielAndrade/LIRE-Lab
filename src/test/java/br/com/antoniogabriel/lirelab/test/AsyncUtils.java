package br.com.antoniogabriel.lirelab.test;

import javafx.beans.value.ObservableBooleanValue;
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

}
