package br.com.antoniogabriel.lirelab.app;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock private Stage stage;
    @Mock private AppFXML appFxml;

    @InjectMocks private App app = new App();

    @Test
    public void shouldMaximizeStage() throws Exception {
        app.start(stage);

        verify(stage).setMaximized(true);
    }

    @Test
    public void shouldLoadAppFXML() throws Exception {
        app.start(stage);

        verify(appFxml).loadIn(stage);
    }
}