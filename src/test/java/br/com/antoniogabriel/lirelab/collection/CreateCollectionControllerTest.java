package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.ControllerTest;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import com.google.inject.AbstractModule;
import javafx.event.ActionEvent;
import javafx.stage.Window;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionControllerTest extends ControllerTest<CreateCollectionFXML, CreateCollectionController> {

    private static final String SOME_PATH = "/some/path";
    private static final String EMPTY = "";
    public static final String MY_COLLECTION = "My Collection";
    public static final String SOME_DIR = "/some/dir";
    public static final List<Feature> SOME_FEATURES = Arrays.asList(CEDD, TAMURA);

    @Mock private DialogProvider dialogProvider;
    @Mock private ActionEvent event;
    @Mock private Window window;
    @Mock private File file;
    @Mock private CollectionService service;
    @Mock private CreateCollectionTask task;
    @Mock private ProgressDialog progressDialog;

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(DialogProvider.class).toInstance(dialogProvider);
                bind(CollectionService.class).toInstance(service);
            }
        };
    }

    @Override
    public void init() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(file.getAbsolutePath()).willReturn(SOME_PATH);
    }

    @Test
    public void shouldSetImagesDirectoryWhenUserChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(file);

        controller.chooseImagesDirectory(event);

        assertThat(controller.imagesDirectory(), is(SOME_PATH));
    }

    @Test
    public void shouldNotSetImagesDirectoryWhenUserDontChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(null);

        controller.chooseImagesDirectory(event);

        assertThat(controller.imagesDirectory(), is(EMPTY));
    }

    @Test
    public void shouldCloseWindow() throws Exception {
        controller.close(event);

        verify(window).hide();
    }

    @Test
    public void shouldStartCreateCollectionTaskAndShowProgress() throws Exception {
        given(service.getTaskToCreateCollection(MY_COLLECTION, SOME_DIR, SOME_FEATURES))
                .willReturn(task);
        given(dialogProvider.getProgressDialog(task, window))
                .willReturn(progressDialog);

        controller.collectionName(MY_COLLECTION);
        controller.imagesDirectory(SOME_DIR);
        controller.collectionFeatures(SOME_FEATURES);

        controller.createCollection(event);

        verify(progressDialog).showAndStart();
    }
}