package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.custom.FeatureTable;
import br.com.antoniogabriel.lirelab.custom.ViewableFeature;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import br.com.antoniogabriel.lirelab.util.ProgressDialog;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateCollectionControllerTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    private static final String SOME_PATH = "/some/path";
    private static final String SOME_COLLECTION = "My Collection";
    private static final String SOME_DIR = "/some/dir";

    @Mock private DialogProvider dialogProvider;
    @Mock private ActionEvent event;
    @Mock private Window window;
    @Mock private File file;
    @Mock private CreateCollectionTask task;

    @Mock private CollectionService service;
    @Mock private ProgressDialog progressDialog;
    @Mock private FeatureUtils featureUtils;

    @Mock private TextField nameField;
    @Mock private TextField imagesDirectoryField;
    @Mock private FeatureTable featuresTable;
    @Mock private Button createButton;
    @Mock private ObservableList<ViewableFeature> viewableFeatures;
    @Mock private StringProperty textProperty;
    @Mock private BooleanBinding noFeatureSelectedBinding;
    @Mock private BooleanBinding isEmptyBinding;
    @Mock private BooleanProperty disableProperty;

    private List<Feature> features = Arrays.asList(CEDD, TAMURA);

    @InjectMocks
    private CreateCollectionController controller =
            new CreateCollectionController(dialogProvider, service, featureUtils);

    @Before
    public void setUp() throws Exception {
        given(dialogProvider.getWindowFrom(event)).willReturn(window);
        given(file.getAbsolutePath()).willReturn(SOME_PATH);

        given(nameField.textProperty()).willReturn(textProperty);
        given(imagesDirectoryField.textProperty()).willReturn(textProperty);
        given(featuresTable.noFeatureSelected()).willReturn(noFeatureSelectedBinding);
        given(createButton.disableProperty()).willReturn(disableProperty);
        given(textProperty.isEmpty()).willReturn(isEmptyBinding);
    }

    @Test
    public void shouldPopulateTableWhenInitialize() throws Exception {
        given(featureUtils.toViewable(Feature.values())).willReturn(viewableFeatures);

        controller.initialize(null, null);

        verify(featuresTable).setItems(viewableFeatures);
    }

    @Test
    public void shouldBindCreateButtonToDisableWhenOtherFieldsAreEmpty() throws Exception {
        controller.initialize(null, null);

        verify(disableProperty).bind(isEmptyBinding.or(isEmptyBinding.or(noFeatureSelectedBinding)));
    }

    @Test
    public void shouldSetImagesDirectoryWhenUserChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(file);

        controller.chooseImagesDirectory(event);

        verify(imagesDirectoryField).setText(SOME_PATH);
    }

    @Test
    public void shouldNotSetImagesDirectoryWhenUserDontChoosesIt() throws Exception {
        given(dialogProvider.chooseImagesDirectory(window)).willReturn(null);

        controller.chooseImagesDirectory(event);

        verify(imagesDirectoryField, never()).setText(SOME_PATH);
    }

    @Test
    public void shouldCloseWindow() throws Exception {
        controller.close(event);

        verify(window).hide();
    }

    @Test
    public void shouldStartCreateCollectionTaskAndShowProgress() throws Exception {
        given(nameField.getText()).willReturn(SOME_COLLECTION);
        given(imagesDirectoryField.getText()).willReturn(SOME_DIR);
        given(featuresTable.getSelectedFeatures()).willReturn(features);

        given(service.getTaskToCreateCollection(anyString(), anyString(), anyList()))
                .willReturn(task);
        given(dialogProvider.getProgressDialog(task, window))
                .willReturn(progressDialog);

        controller.createCollection(event);

        verify(progressDialog).showAndStart();
    }
}