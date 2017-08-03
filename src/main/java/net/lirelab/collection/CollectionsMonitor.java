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

import net.lirelab.app.DirectoryStructure;
import com.google.inject.Inject;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

@Singleton
public class CollectionsMonitor {

    private PathResolver resolver;

    private DirectoryStructure directoryStructure;

    private List<Runnable> listeners = new ArrayList<>();

    @Inject
    public CollectionsMonitor(PathResolver resolver, DirectoryStructure directoryStructure) {
        this.resolver = resolver;
        this.directoryStructure = directoryStructure;
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void bindListenersTo(CreateCollectionTask task) {
        task.setOnSucceeded(event -> executeListeners());
    }

    public void executeListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    public boolean hasListener(Runnable aListener) {
        return listeners.contains(aListener);
    }

    public void startMonitoringCollectionsModificationsInFileSystem() throws IOException {
        Path path = Paths.get(resolver.getCollectionsPath());

        if(!Files.exists(path)) {
            directoryStructure.createCollectionsDirectory();
        }

        // We obtain the file system of the Path
        FileSystem fs = path.getFileSystem();

        WatchService watcher = fs.newWatchService();

        // We register the path to the watcher
        path.register(watcher, ENTRY_DELETE, ENTRY_MODIFY, ENTRY_CREATE);

        Runnable loop = () -> {

                // Start the infinite polling loop
                WatchKey key = null;

                while (true) {
                    try {
                        key = watcher.take();
                    } catch (InterruptedException e) {
                        continue;
                    }

                    // Dequeueing events
                    WatchEvent.Kind<?> kind = null;
                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        // Get the type of the event
                        kind = watchEvent.kind();
                        if (OVERFLOW == kind) {
                            continue; //loop
                        } else if (ENTRY_DELETE == kind || ENTRY_MODIFY == kind || ENTRY_CREATE == kind) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                CollectionsMonitor.this.executeListeners();
                            }
                        }
                    }

                    if (!key.reset()) {
                        break; //loop
                    }
                }
        };

        Thread t = new Thread(loop);
        t.setDaemon(true);
        t.start();
    }
}
