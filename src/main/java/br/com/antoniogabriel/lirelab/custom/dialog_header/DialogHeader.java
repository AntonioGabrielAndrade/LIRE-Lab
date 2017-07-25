package br.com.antoniogabriel.lirelab.custom.dialog_header;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;

public class DialogHeader extends BorderPane {

    private static final String DIALOG_HEADER_FXML = "dialog-header.fxml";

    @FXML private Label title;
    @FXML private Text hint;

    public DialogHeader() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DIALOG_HEADER_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public StringProperty titleProperty() {
        return title.textProperty();
    }

    public String getHint() {
        return hint.getText();
    }

    public StringProperty hintProperty() {
        return hint.textProperty();
    }

    public ObjectProperty<Paint> hintColor() {
        return hint.fillProperty();
    }

    public void setHint(String hint) {
        this.hint.setText(hint);
    }
}
