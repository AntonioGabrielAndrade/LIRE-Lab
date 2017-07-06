package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.test.TimeOut;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.test.AsyncUtils.waitUntil;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ProgressDialogViewObject extends FxRobot {

    public void checkStructure() {
        verifyThat("#progress-bar", isVisible());
        verifyThat("#message", isVisible());
        verifyThat("#ok-button", isVisible());
    }

    public ProgressDialogViewObject checkProgressMark(Integer... percentages) throws TimeoutException {
        for (Integer percentage : percentages) {
            waitUntil(progressEqualsTo(percentage));
        }

        return this;
    }

    public ProgressDialogViewObject checkMessageShow(String... values) throws TimeoutException {
        for (String value : values) {
            waitUntil(messageEqualsTo(value));
        }

        return this;
    }

    public ProgressDialogViewObject checkOkIsEnabledWhenFinish() throws TimeoutException {
        waitUntil(progressComplete().and(buttonEnabled()), TimeOut.of(15, SECONDS));

        return this;
    }

    public void checkErrorAreaShow(String error) throws TimeoutException {
        waitUntil(dialogPaneIsExpanded());
        assertThat("Error was not printed", errorAreaContains(error));
    }

    public void ok() {
        clickOn("#ok-button").interrupt();
    }

    private double percentToDecimal(Integer percentage) {
        return percentage / 100.00;
    }

    private BooleanBinding progressEqualsTo(Integer percentage) {
        return progressBar().progressProperty().isEqualTo(
                percentToDecimal(percentage), 0.05);
    }

    private ProgressBar progressBar() {
        return lookup("#progress-bar").query();
    }

    private BooleanBinding messageEqualsTo(String value) {
        return message().textProperty().isEqualTo(value);
    }

    private Text message() {
        return lookup("#message").query();
    }

    private BooleanBinding progressComplete() {
        return progressBar().progressProperty().isEqualTo(1.0, 0);
    }

    private BooleanBinding buttonEnabled() {
        return okButton().disabledProperty().not();
    }

    private Node okButton() {
        return lookup("#ok-button").query();
    }

    private boolean errorAreaContains(String message) {
        return errorArea().getText().contains(message);
    }

    private TextArea errorArea() {
        return lookup("#error-message").query();
    }

    private BooleanProperty dialogPaneIsExpanded() {
        return dialogPane().expandedProperty();
    }

    private DialogPane dialogPane() {
        return lookup("#progress-dialog-pane").query();
    }
}
