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

import net.lirelab.custom.progress_dialog.ProgressDialog;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ProgressDialogAcceptanceTest extends ApplicationTest {

    private static final Exception EXCEPTION = new RuntimeException("Some Error!");

    private StubTask task;
    private ProgressDialog dialog;
    private ProgressDialogViewObject view = new ProgressDialogViewObject();

    @Override
    public void start(Stage stage) throws Exception {}

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
        interact(() -> dialog.close());
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkStructure();
    }

    @Test
    public void shouldUpdateProgress() throws Exception {
        view.checkProgressMarks(percent(20),
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
