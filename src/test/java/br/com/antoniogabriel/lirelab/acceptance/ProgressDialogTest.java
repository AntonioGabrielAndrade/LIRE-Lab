package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ProgressDialogTest extends ApplicationTest {

    private static final Exception EXCEPTION = new RuntimeException("Some Error!");

    private StubTask task;
    private ProgressDialog dialog;
    private ProgressDialogViewObject view = new ProgressDialogViewObject();

    @Override
    public void start(Stage stage) throws Exception {
    }

    @Before
    public void setup() {
        interact(() -> {
            task = new StubTask();
            dialog = new ProgressDialog(task);
            dialog.setTitle("Testing ProgressDialog");
            dialog.showAndStart();
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
        view.checkStructure();
    }

    @Test
    public void shouldUpdateProgress() throws Exception {
        view.checkProgressMark(percent(20),
                            percent(40),
                            percent(60),
                            percent(80),
                            percent(100));
    }


    @Test
    public void shouldUpdateMessage() throws Exception {
        view.checkMessageShow("Progress: 20%",
                            "Progress: 40%",
                            "Progress: 60%",
                            "Progress: 80%",
                            "Progress: 100%");
    }

    @Test
    public void shouldEnableOkButtonWhenFinish() throws Exception {
        view.checkOkIsEnabledWhenFinish();
    }

    @Test
    public void shouldShowErrorMessageWhenExceptionOccur() throws Exception {
        task.throwException = true;
        view.checkErrorAreaShow(EXCEPTION.getMessage());
    }

    private int percent(int i) {
        return i;
    }

    private class StubTask extends Task<Void> {
        protected boolean throwException = false;

        @Override
        protected Void call() throws Exception {
            for (int i = 0; i <= 100; i += 20) {
                updateProgress(i, 100);
                updateMessage("Progress: " + percent(i) + "%");
                if(throwException && i==60) {
                    throw EXCEPTION;
                }
                sleep(250);
            }
            return null;
        }
    }
}
