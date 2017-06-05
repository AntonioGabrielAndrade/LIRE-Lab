package br.com.antoniogabriel.lirelab.util;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ProgressDialogTest extends ApplicationTest {

    private Task<Void> task;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private Text message;
    private Node button;

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Before
    public void setup() {
        interact(() -> {
            task = new StubTask();
            dialog = new ProgressDialog(task);
            dialog.show();
            progressBar = lookup("#progress-bar").query();
            message = lookup("#message").query();
            button = lookup("#ok-button").query();
        });
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        verifyThat("#progress-bar", isVisible());
        verifyThat("#message", isVisible());
        verifyThat("#ok-button", isDisabled());
    }

    @Test
    public void shouldUpdateProgress() throws Exception {
        new Thread(task).start();

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.1, 0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.2, 0.05));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.3, 0.05));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.4, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.5, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.6, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.7, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.8, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(0.9, 0.0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(1.0, 0.0));


    }

    @Test
    public void shouldUpdateMessage() throws Exception {
        new Thread(task).start();

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 1 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 2 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 3 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 4 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 5 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 6 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 7 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 8 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 9 of 10"));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                message.textProperty().isEqualTo("Progress: 10 of 10"));

    }

    @Test
    public void shouldEnableOkButtonOnFinish() throws Exception {
        new Thread(task).start();

        WaitForAsyncUtils.waitFor(15,
                TimeUnit.SECONDS,
                progressBar.progressProperty().isEqualTo(1.0, 0));

        WaitForAsyncUtils.waitFor(10,
                TimeUnit.SECONDS,
                button.disabledProperty().not());
    }

    private class StubTask extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            int max = 10;
            for (int i = 0; i <= max; i++) {
                updateProgress(i, max);
                updateMessage(
                        "Progress: " + i + " of " + max);
                sleep(1000);
            }
            return null;
        }
    }
}
