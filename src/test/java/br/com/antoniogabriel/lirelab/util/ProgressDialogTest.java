package br.com.antoniogabriel.lirelab.util;

import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeUnit;

public class ProgressDialogTest extends ApplicationTest {

    private Task<Void> task;
    private ProgressDialog dialog;
    private ProgressDialogView view = new ProgressDialogView();

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Before
    public void setup() {
        interact(() -> {
            task = new StubTask();
            dialog = new ProgressDialog(task);
            dialog.setTitle("Testing ProgressDialog");
            dialog.show();
        });
    }

    @After
    public void tearDown() throws Exception {
        interact(() -> {
           dialog.close();
        });
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkUIStructure();
    }

    @Test
    public void shouldUpdateProgress() throws Exception {
        new Thread(task).start();

        view.checkProgressMark(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
    }

    @Test
    public void shouldUpdateMessage() throws Exception {
        new Thread(task).start();

        view.checkMessageShow("Progress: 1 of 10",
                "Progress: 2 of 10",
                "Progress: 3 of 10",
                "Progress: 4 of 10",
                "Progress: 5 of 10",
                "Progress: 6 of 10",
                "Progress: 7 of 10",
                "Progress: 8 of 10",
                "Progress: 9 of 10",
                "Progress: 10 of 10");
    }

    @Test
    public void shouldEnableOkButtonWhenFinish() throws Exception {
        new Thread(task).start();

        view.checkOkIsEnabledWhenFinish(100, TimeUnit.SECONDS);
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
