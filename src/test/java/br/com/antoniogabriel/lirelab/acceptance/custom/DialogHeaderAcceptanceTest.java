package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.dialog_header.DialogHeader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class DialogHeaderAcceptanceTest extends ApplicationTest {

    private DialogHeader dialogHeader;

    @Override
    public void start(Stage stage) throws Exception {
        dialogHeader = new DialogHeader();
        dialogHeader.setTitle("Dialog Title");
        dialogHeader.setHint("Some dialog hint here");


        BorderPane root = new BorderPane();
        root.setTop(dialogHeader);

        StackPane center = new StackPane(new Text("dialog content"));
        center.setPrefHeight(500);

        root.setCenter(center);
        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void shouldShowTitle() throws Exception {
        verifyThat("#dialog-header-title", hasText("Dialog Title"));
    }

    @Test
    public void shouldShowHint() throws Exception {
        verifyThat("#dialog-header-hint", hasText("Some dialog hint here"));
    }
}
