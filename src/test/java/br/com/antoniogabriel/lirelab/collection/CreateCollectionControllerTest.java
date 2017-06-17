package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionControllerTest extends ApplicationTest {

    private static final String SOME_PATH = "/some/path";
    private static final String EMPTY = "";

    @Inject private CreateCollectionFXML fxml;

    @Mock private DialogProvider dialogProvider;
    @Mock private ActionEvent event;
    @Mock private Window window;
    @Mock private File file;

    private CreateCollectionController controller;

    private Collection<Module> modules = Arrays.asList(new AbstractModule() {
        @Override
        protected void configure() {
            bind(DialogProvider.class).toInstance(dialogProvider);
        }
    });

    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this, modules);
        controller = fxml.getController();
    }

    @Before
    public void setUp() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(file.getAbsolutePath()).willReturn(SOME_PATH);
    }

    @Test
    public void shouldSetImagesDirectoryToFieldWhenUserChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(file);

        controller.chooseImagesDirectory(event);

        assertThat(controller.imagesDirectory(), is(SOME_PATH));
    }

    @Test
    public void shouldNotSetImagesDirectoryToFieldWhenUserDontChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(null);

        controller.chooseImagesDirectory(event);

        assertThat(controller.imagesDirectory(), is(EMPTY));
    }

    @Test
    public void shouldCloseWindow() throws Exception {
        controller.close(event);

        verify(window).hide();
    }
}