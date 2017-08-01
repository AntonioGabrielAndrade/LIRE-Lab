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

package br.com.antoniogabriel.lirelab.custom.progress_dialog;

import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ProgressDialog extends Dialog<Void> {

    private Task<Void> task;
    private ProgressBar progressBar;
    private Text message;
    private Button cancelButton;

    public ProgressDialog(Task<Void> task) {
        this.task = task;
        setupProgressBar();
        setupMessageText();
        setupHeaderText();
        setupOkButton();
        setupCancelButton();
        setupWindowBehavior();
        setupExceptionHandling();
        setupDialogContent();
    }

    public void showAndStart() {
        show();
        new Thread(task).start();
    }

    private void setupProgressBar() {
        progressBar = new ProgressBar();
        progressBar.setId("progress-bar");
        progressBar.setPrefWidth(500);
        progressBar.setMaxHeight(10);
        progressBar.requestFocus();
        bindProgressBarTo(taskProgress());
    }

    private void setupMessageText() {
        message = new Text();
        message.setId("message");
        bindProgressMessageTo(taskMessage());
    }

    private void setupHeaderText() {
        bindHeaderMessageTo(taskTitle());
    }

    private void setupOkButton() {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);
        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        okButton.setId("ok-button");
        okButton.disableProperty().bind(
                    taskNotCompleted()
                        .and(noTaskException())
                            .or(taskIsRunning())
        );
    }

    private void setupCancelButton() {
        cancelButton = new Button("");
        cancelButton.setTooltip(new Tooltip("Cancel"));
        cancelButton.setOnAction(event -> close());
        cancelButton.setGraphic(new TangoIconWrapper("actions:process-stop"));
        cancelButton.setFocusTraversable(false);
        cancelButton.disableProperty().bind(taskCompleted());
    }

    public void setOnCancel(EventHandler<ActionEvent> eventHandler) {
        cancelButton.setOnAction(eventHandler);
    }

    private void setupWindowBehavior() {
        hideOwnerOnClose();

        // dirty hack to fix JavaFX resizing bug
        resizeStageWhenDialogExpandOrCollapse();
    }

    private void resizeStageWhenDialogExpandOrCollapse() {
        getDialogPane().expandedProperty().addListener((l) -> {
            Platform.runLater(() -> {
                if(isShowing()) {
                    Stage stage = (Stage) getDialogPane().getScene().getWindow();
                    stage.sizeToScene();
                }
            });
        });
    }

    private void hideOwnerOnClose() {
        setOnCloseRequest(event -> {
            if(task.isRunning()) {
                task.cancel();
            }

            if (getOwner() != null) {
                getOwner().hide();
            }
        });
    }

    private void setupExceptionHandling() {
        taskException().addListener((observable, oldException, newException) -> {
            if(newException != null) {
                String stackTrace = getStacktraceFrom(newException);
                Node errorPane = getErrorPaneWith(stackTrace);
                setExpandableContentAs(errorPane);
                expandDialog();
            }
        });
    }

    private String getStacktraceFrom(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }


    private Node getErrorPaneWith(String error) {
        Label label = new Label("Some error occurred:");
        TextArea textArea = getErrorArea(error);
        return errorGrid(label, textArea);
    }

    private GridPane errorGrid(Label label, TextArea textArea) {
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane grid = new GridPane();
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setVgap(3);
        grid.add(label, 0, 0);
        grid.add(textArea, 0, 1);
        return grid;
    }

    private TextArea getErrorArea(String error) {
        TextArea textArea = new TextArea();
        textArea.setId("error-message");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setFont(Font.font("Arial"));
        textArea.setStyle("-fx-text-fill: red");
        textArea.setPrefWidth(500);
        textArea.setPrefHeight(200);
        textArea.setText(error);
        return textArea;
    }

    private void setupDialogContent() {
        getDialogPane().setId("progress-dialog-pane");

        VBox vbox = new VBox();
        vbox.setSpacing(2);
        vbox.getChildren().addAll(message, progressBox());
        getDialogPane().setContent(vbox);
        initStyle(StageStyle.UNDECORATED);
    }

    private HBox progressBox() {
        HBox box = new HBox();
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(progressBar, cancelButton);
        return box;
    }

    private void bindProgressBarTo(ReadOnlyDoubleProperty property) {
        barProgress().bind(property);
    }

    private void bindProgressMessageTo(ReadOnlyStringProperty property) {
        progressbarMessage().bind(property);
    }

    private void bindHeaderMessageTo(ReadOnlyStringProperty property) {
        dialogHeader().bind(property);
    }

    private DoubleProperty barProgress() {
        return progressBar.progressProperty();
    }

    private ReadOnlyDoubleProperty taskProgress() {
        return this.task.progressProperty();
    }

    private StringProperty progressbarMessage() {
        return message.textProperty();
    }

    private StringProperty dialogHeader() {
        return this.headerTextProperty();
    }

    private ReadOnlyStringProperty taskMessage() {
        return this.task.messageProperty();
    }

    private ReadOnlyStringProperty taskTitle() {
        return this.task.titleProperty();
    }

    private BooleanBinding taskNotCompleted() {
        return taskProgress().isEqualTo(1.0, 0.0).not();
    }

    private BooleanBinding taskCompleted() {
        return taskProgress().isEqualTo(1.0, 0.0);
    }

    private BooleanBinding noTaskException() {
        return taskException().isNull();
    }

    private ReadOnlyBooleanProperty taskIsRunning() {
        return this.task.runningProperty();
    }

    private void setExpandableContentAs(Node errorPane) {
        getDialogPane().setExpandableContent(errorPane);
    }

    private void expandDialog() {
        getDialogPane().setExpanded(true);
    }

    private ReadOnlyObjectProperty<Throwable> taskException() {
        return this.task.exceptionProperty();
    }
}
