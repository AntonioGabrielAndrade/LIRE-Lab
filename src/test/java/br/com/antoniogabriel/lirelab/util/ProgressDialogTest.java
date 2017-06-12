package br.com.antoniogabriel.lirelab.util;

import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeUnit;

public class ProgressDialogTest extends ApplicationTest {

    private static final Exception EXCEPTION = new RuntimeException("Some Error!");

    private StubTask task;
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

        view.checkProgressMark(0.2, 0.4, 0.6, 0.8, 1.0);
    }

    @Test
    public void shouldUpdateMessage() throws Exception {
        new Thread(task).start();

        view.checkMessageShow("Progress: 1 of 5",
                "Progress: 2 of 5",
                "Progress: 3 of 5",
                "Progress: 4 of 5",
                "Progress: 5 of 5");
    }

    @Test
    public void shouldEnableOkButtonWhenFinish() throws Exception {
        new Thread(task).start();

        view.checkOkIsEnabledWhenFinish(100, TimeUnit.SECONDS);
    }

    @Test
    public void shouldShowErrorMessageWhenExceptionOccur() throws Exception {
        task.throwException = true;
        new Thread(task).start();

        view.checkErrorMessageShown(EXCEPTION.getMessage());
    }



    private class StubTask extends Task<Void> {
        protected boolean throwException = false;

        @Override
        protected Void call() throws Exception {
            int max = 5;
            for (int i = 0; i <= max; i++) {
                updateProgress(i, max);
                updateMessage("Progress: " + i + " of " + max);
                if(throwException && i==3) {
                    throw EXCEPTION;
                }
                sleep(1000);
            }
            return null;
        }
    }
}
