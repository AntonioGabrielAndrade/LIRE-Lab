package br.com.antoniogabriel.lirelab.util;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProgressDialog extends Dialog<Void> {

    private Task<Void> task;
    private ProgressBar progressBar;
    private Text message;
    private Node okButton;

    public ProgressDialog(Task<Void> task) {
        this.task = task;
        setupUI();
        progressBar.progressProperty().bind(task.progressProperty());
        message.textProperty().bind(task.messageProperty());
        okButton.disableProperty().bind(task.runningProperty());
        setOnCloseRequest(event -> {
            getOwner().hide();
        });
    }

    private void setupUI() {
        setTitle("Creating collection");
        message = new Text();
        message.setId("message");

        progressBar = new ProgressBar();
        progressBar.setId("progress-bar");
        progressBar.setPrefWidth(500);
        progressBar.setMaxHeight(10);

        VBox vbox = new VBox();
        vbox.setSpacing(2);
        vbox.getChildren().addAll(message, progressBar);

        getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);
        okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setId("ok-button");
        okButton.setDisable(true);
    }
}
