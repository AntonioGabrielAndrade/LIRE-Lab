package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import javafx.event.ActionEvent;
import javafx.stage.Window;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ToolBarControllerControllerTest {

    @Mock private Window window;
    @Mock private CreateCollectionFXML createCollectionFXML;
    @Mock private ActionEvent event;
    @Mock private DialogProvider dialogProvider;

    @InjectMocks private ToolBarController controller;

    @Test
    public void shouldOpenCreateCollectionDialog() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);

        controller.openCreateCollectionDialog(event);

        verify(createCollectionFXML).loadOwnedBy(window);
    }
}