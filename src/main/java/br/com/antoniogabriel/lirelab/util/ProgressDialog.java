package br.com.antoniogabriel.lirelab.util;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ProgressDialog extends Dialog<Void> {

    private Task<Void> task;
    private ProgressBar progressBar;
    private Text message;

    public ProgressDialog(Task<Void> task) {
        this.task = task;
        setupProgressBar();
        setupMessageText();
        setupOkButton();
        setupWindowBehavior();
        setupExceptionHandling();
        setupDialogContent();
    }

    private void setupProgressBar() {
        progressBar = new ProgressBar();
        progressBar.setId("progress-bar");
        progressBar.setPrefWidth(500);
        progressBar.setMaxHeight(10);
        progressBar.progressProperty().bind(this.task.progressProperty());
    }

    private void setupMessageText() {
        message = new Text();
        message.setId("message");
        message.textProperty().bind(this.task.messageProperty());
    }

    private void setupOkButton() {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);
        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        okButton.setId("ok-button");
        okButton.disableProperty().bind(
                this.task
                        .progressProperty().isEqualTo(1.0, 0.0).not()
                        .and(task.exceptionProperty().isNull())
                            .or(this.task.runningProperty())
        );
    }

    private void setupWindowBehavior() {
        setOnCloseRequest(event -> {
            if (getOwner() != null) {
                getOwner().hide();
            }
        });

        // dirty hack to fix JavaFX resizing bug
        getDialogPane().expandedProperty().addListener((l) -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) getDialogPane().getScene().getWindow();
                stage.sizeToScene();
            });
        });
    }

    private void setupExceptionHandling() {
        this.task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                newValue.printStackTrace(pw);
                String stackTrace = sw.toString();
                getDialogPane().setExpandableContent(getErrorPane(stackTrace));
                getDialogPane().setExpanded(true);
            }
        });
    }

    private Node getErrorPane(String error) {
        Label label = new Label("Some error occurred:");

        TextArea textArea = new TextArea();
        textArea.setId("error-message");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setFont(Font.font("Arial"));
        textArea.setStyle("-fx-text-fill: red");
        textArea.setPrefWidth(500);
        textArea.setPrefHeight(200);
        textArea.setText(error);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane grid = new GridPane();
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setVgap(3);
        grid.add(label, 0, 0);
        grid.add(textArea, 0, 1);

        return grid;
    }

    private void setupDialogContent() {
        getDialogPane().setId("progress-dialog-pane");
        setTitle("Creating collection");

        VBox vbox = new VBox();
        vbox.setSpacing(2);
        vbox.getChildren().addAll(message, progressBar);
        getDialogPane().setContent(vbox);
    }
}
