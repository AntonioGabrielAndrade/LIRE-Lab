/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lirelab.collection;

import net.lirelab.acceptance.CollectionTestHelper;
import net.lirelab.app.DirectoryStructure;
import net.lirelab.lire.SimpleIndexCreator;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static net.lirelab.test_utilities.TestConstants.TEST_ROOT;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CollectionsMonitorTest {

    public static final Collection COLLECTION_1 = new Collection("Collection1");
    public static final Collection COLLECTION_2 = new Collection("Collection2");

    private CollectionsMonitor monitor;

    private SimpleBooleanProperty listenerExecuted = new SimpleBooleanProperty(false);
    private Runnable SOME_LISTENER = () -> listenerExecuted.set(true);

    @Mock private SimpleIndexCreator indexCreator;
    @Mock private ThumbnailsCreator thumbCreator;
    @Mock private XMLCreator xmlCreator;

    private final PathResolver resolver = new PathResolver(TEST_ROOT);
    private CollectionTestHelper helper = new CollectionTestHelper(resolver);

    @Before
    public void setUp() throws Exception {
        startJavaFXThread();
        createMonitor();
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
        monitor.startMonitoringCollectionsModificationsInFileSystem();

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
        return new CreateCollectionTask(new CreateCollectionRunner(indexCreator, thumbCreator, xmlCreator));
    }

}
