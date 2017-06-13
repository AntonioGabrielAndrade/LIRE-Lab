package br.com.antoniogabriel.lirelab.util;

import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            WaitForAsyncUtils.waitFor(5,
                    TimeUnit.SECONDS,
                    bar.progressProperty().isEqualTo(
                            percentToDecimal(percentage), 0.05));
        }

        return this;
    }


    public ProgressDialogView checkMessageShow(String... values) throws TimeoutException {
        Text message = lookup("#message").query();

        for (String value : values) {
            WaitForAsyncUtils.waitFor(5,
                    TimeUnit.SECONDS,
                    message.textProperty().isEqualTo(value));
        }

        return this;
    }

    public ProgressDialogView checkOkIsEnabledWhenFinish() throws TimeoutException {
        return checkOkIsEnabledWhenFinish(15, TimeUnit.SECONDS);
    }

    public ProgressDialogView checkOkIsEnabledWhenFinish(long timeout, TimeUnit timeUnit) throws TimeoutException {
        ProgressBar bar = lookup("#progress-bar").query();
        Node button = lookup("#ok-button").query();

        WaitForAsyncUtils.waitFor(timeout,
                timeUnit,
                bar.progressProperty().isEqualTo(1.0, 0)
                        .and(button.disabledProperty().not()));

        return this;
    }

    public void ok() {
        clickOn("#ok-button").interrupt();
    }

    public void checkErrorMessageShown(String message) throws TimeoutException {
        DialogPane pane = lookup("#progress-dialog-pane").query();

        WaitForAsyncUtils.waitFor(5,
                TimeUnit.SECONDS,
                pane.expandedProperty());

        TextArea error = lookup("#error-message").query();

        assertThat("Error was printed", error.getText().contains(message));
    }

    private double percentToDecimal(Integer percentage) {
        return percentage/100.00;
    }
}
