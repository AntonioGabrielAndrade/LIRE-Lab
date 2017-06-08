package br.com.antoniogabriel.lirelab.util;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ProgressDialogView extends FxRobot {

    public void checkUIStructure() {
        verifyThat("#progress-bar", isVisible());
        verifyThat("#message", isVisible());
        verifyThat("#ok-button", isVisible());
    }

    public ProgressDialogView checkProgressMark(Double... marks) throws TimeoutException {
        ProgressBar bar = lookup("#progress-bar").query();

        for (Double mark : marks) {
            WaitForAsyncUtils.waitFor(100,
                    TimeUnit.SECONDS,
                    bar.progressProperty().isEqualTo(mark, 0.05));
        }

        return this;
    }

    public ProgressDialogView checkMessageShow(String... values) throws TimeoutException {
        Text message = lookup("#message").query();

        for (String value : values) {
            WaitForAsyncUtils.waitFor(100,
                    TimeUnit.SECONDS,
                    message.textProperty().isEqualTo(value));
        }

        return this;
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
}
