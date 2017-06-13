package br.com.antoniogabriel.lirelab.util;

import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ProgressDialogView extends FxRobot {

    public void checkStructure() {
        verifyThat("#progress-bar", isVisible());
        verifyThat("#message", isVisible());
        verifyThat("#ok-button", isVisible());
    }

    public ProgressDialogView checkProgressMark(Integer... percentages) throws TimeoutException {
        ProgressBar bar = lookup("#progress-bar").query();

        for (Integer percentage : percentages) {
            waitUntil(bar.progressProperty().isEqualTo(
                            percentToDecimal(percentage), 0.05));
        }

        return this;
    }

    public ProgressDialogView checkMessageShow(String... values) throws TimeoutException {
        Text message = lookup("#message").query();

        for (String value : values) {
            waitUntil(message.textProperty().isEqualTo(value));
        }

        return this;
    }

    public ProgressDialogView checkOkIsEnabledWhenFinish() throws TimeoutException {
        ProgressBar bar = lookup("#progress-bar").query();
        Node button = lookup("#ok-button").query();

        waitUntil(bar.progressProperty().isEqualTo(1.0, 0)
                        .and(button.disabledProperty().not()),
                new TimeOut(15, SECONDS));

        return this;
    }

    public void ok() {
        clickOn("#ok-button").interrupt();
    }

    public void checkErrorMessageShown(String message) throws TimeoutException {
        DialogPane pane = lookup("#progress-dialog-pane").query();

        waitUntil(pane.expandedProperty());

        TextArea error = lookup("#error-message").query();

        assertThat("Error was printed", error.getText().contains(message));
    }

    private void waitUntil(ObservableBooleanValue condition) throws TimeoutException {
        waitUntil(condition, new TimeOut(5, SECONDS));
    }

    private void waitUntil(ObservableBooleanValue condition, TimeOut timeout) throws TimeoutException {
        WaitForAsyncUtils.waitFor(timeout.value, timeout.unit, condition);
    }

    private double percentToDecimal(Integer percentage) {
        return percentage/100.00;
    }

    private class TimeOut {
        private final long value;
        private final TimeUnit unit;

        public TimeOut(long value, TimeUnit unit) {
            this.value = value;
            this.unit = unit;
        }
    }
}
