package br.com.antoniogabriel.lirelab.app;

import javafx.event.ActionEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeControllerTest {

    @Mock private ToolBarController toolBarController;
    @InjectMocks private WelcomeController welcomeController;

    private ActionEvent event = new ActionEvent();

    @Test
    public void shouldOpenCreateCollectionDialog() throws Exception {
        welcomeController.openCreateCollectionDialog(event);

        verify(toolBarController).openCreateCollectionDialog(event);
    }
}