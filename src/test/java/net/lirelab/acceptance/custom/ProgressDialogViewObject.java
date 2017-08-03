/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lirelab.acceptance.custom;

import net.lirelab.test_utilities.TimeOut;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import net.lirelab.test_utilities.AsyncUtils;
import org.testfx.api.FxRobot;

import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.AsyncUtils.waitUntil;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ProgressDialogViewObject extends FxRobot {

    public void checkStructure() {
        verifyThat("#progress-bar", isVisible());
        verifyThat("#message", isVisible());
        verifyThat("#ok-button", isVisible());
    }

    public ProgressDialogViewObject checkProgressMarks(Integer... percentages) throws TimeoutException {
        for (Integer percentage : percentages) {
            AsyncUtils.waitUntil(progressEqualsTo(percentage));
        }

        return this;
    }

    public ProgressDialogViewObject checkMessageShow(String... values) throws TimeoutException {
        for (String value : values) {
            AsyncUtils.waitUntil(messageEqualsTo(value));
        }

        return this;
    }

    public ProgressDialogViewObject checkOkIsEnabledWhenFinish() throws TimeoutException {
        waitUntil(progressComplete().and(buttonEnabled()), TimeOut.of(15, SECONDS));

        return this;
    }

    public void checkErrorAreaShow(String error) throws TimeoutException {
        AsyncUtils.waitUntil(dialogPaneIsExpanded());
        assertTrue(errorAreaContains(error));
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
