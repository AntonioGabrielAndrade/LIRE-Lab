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
        okButton.disableProperty().bind(this.task.progressProperty().isEqualTo(1.0, 0.0).not());
        progressBar.progressProperty().bind(this.task.progressProperty());
        message.textProperty().bind(this.task.messageProperty());
        setOnCloseRequest(event -> {
            if (getOwner() != null) {
                getOwner().hide();
            }
        });
    }

    private void setupUI() {
        setTitle("Creating collection");
        message = new Text();
        message.setId("message");

        progressBar = new ProgressBar();
        progressBar.setProgress(-1);
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
        okButton.setDisable(true);
        okButton.setId("ok-button");
    }
}
