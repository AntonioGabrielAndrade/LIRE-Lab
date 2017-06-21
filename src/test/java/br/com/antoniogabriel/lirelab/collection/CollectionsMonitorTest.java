package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.acceptance.CollectionHelper;
import br.com.antoniogabriel.lirelab.app.DirectoryStructure;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static br.com.antoniogabriel.lirelab.collection.CollectionRepositoryTest.TEST_ROOT;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class CollectionsMonitorTest {

    public static final Collection COLLECTION_1 = new Collection("Collection1");
    public static final Collection COLLECTION_2 = new Collection("Collection2");

    private CollectionsMonitor monitor;

    private SimpleBooleanProperty listenerExecuted = new SimpleBooleanProperty(false);
    private Runnable SOME_LISTENER = () -> listenerExecuted.set(true);

    @Mock private IndexCreator indexCreator;
    @Mock private ThumbnailsCreator thumbCreator;
    @Mock private XMLCreator xmlCreator;

    private final PathResolver resolver = new PathResolver(TEST_ROOT);
    private CollectionHelper helper = new CollectionHelper(resolver);

    @Before
    public void setUp() throws Exception {
        startJavaFXThread();
        configStubs();
        createMonitor();
    }

    private void configStubs() throws IOException, JAXBException {
        doNothing().when(indexCreator).create();
        doNothing().when(thumbCreator).create();
        doNothing().when(xmlCreator).create();
    }

    private void createMonitor() {
        monitor = new CollectionsMonitor(resolver, new DirectoryStructure(resolver));
    }

    @After
    public void tearDown() throws Exception {
        helper.deleteCollection(COLLECTION_1);
        helper.deleteCollection(COLLECTION_2);
    }

    @Test
    public void shouldRegisterListeners() throws Exception {
        monitor.addListener(SOME_LISTENER);

        assertTrue(monitor.hasListener(SOME_LISTENER));
    }

    @Test
    public void shouldExecuteListenersWhenCollectionIsCreated() throws Exception {
        CreateCollectionTask creationTask = getTask();

        monitor.addListener(SOME_LISTENER);
        monitor.bindListenersTo(creationTask);

        runTask(creationTask);

        waitFor(completed(creationTask));
        waitFor(listenerExecuted);
    }


    @Test
    public void shouldExecuteListenersWhenCollectionIsDeleted() throws Exception {
        helper.createStubCollection(COLLECTION_1);
        helper.createStubCollection(COLLECTION_2);

        monitor.addListener(SOME_LISTENER);
        monitor.startMonitoringCollectionsDeleteAndUpdate();

        helper.deleteCollection(COLLECTION_1);

        waitFor(listenerExecuted);
    }

    private BooleanBinding completed(CreateCollectionTask task) {
        return task.runningProperty().not();
    }

    private void waitFor(ObservableBooleanValue condition) throws TimeoutException {
        WaitForAsyncUtils.waitFor(5, SECONDS, condition);
    }

    private void runTask(CreateCollectionTask task) {
        Platform.runLater(() -> new Thread(task).start());
    }

    private void startJavaFXThread() {
        new JFXPanel();
        new FxRobot().interrupt();
    }

    private CreateCollectionTask getTask() {
        return new CreateCollectionTask(indexCreator, thumbCreator, xmlCreator);
    }

}
