package br.com.antoniogabriel.lirelab.custom;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
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

    public void showAndStart() {
        show();
        new Thread(task).start();
    }

    private void setupProgressBar() {
        progressBar = new ProgressBar();
        progressBar.setId("progress-bar");
        progressBar.setPrefWidth(500);
        progressBar.setMaxHeight(10);
        bindProgressBarTo(taskProgress());
    }

    private void setupMessageText() {
        message = new Text();
        message.setId("message");
        bindProgressMessageTo(taskMessage());
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

    private void setupWindowBehavior() {
        hideOwnerOnClose();

        // dirty hack to fix JavaFX resizing bug
        resizeStageWhenDialogExpandOrCollapse();
    }

    private void resizeStageWhenDialogExpandOrCollapse() {
        getDialogPane().expandedProperty().addListener((l) -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) getDialogPane().getScene().getWindow();
                stage.sizeToScene();
            });
        });
    }

    private void hideOwnerOnClose() {
        setOnCloseRequest(event -> {
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
        setTitle("Creating collection");

        VBox vbox = new VBox();
        vbox.setSpacing(2);
        vbox.getChildren().addAll(message, progressBar);
        getDialogPane().setContent(vbox);
    }

    private void bindProgressBarTo(ReadOnlyDoubleProperty property) {
        barProgress().bind(property);
    }

    private void bindProgressMessageTo(ReadOnlyStringProperty property) {
        progressbarMessage().bind(property);
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

    private ReadOnlyStringProperty taskMessage() {
        return this.task.messageProperty();
    }

    private BooleanBinding taskNotCompleted() {
        return taskProgress().isEqualTo(1.0, 0.0).not();
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
