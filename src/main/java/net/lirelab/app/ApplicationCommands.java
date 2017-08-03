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

package net.lirelab.app;

import net.lirelab.collection.Collection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.lirelab.app.ApplicationCommands.CollectionCommand.CREATE;
import static net.lirelab.app.ApplicationCommands.CollectionCommand.DELETE;
import static net.lirelab.app.ApplicationCommands.CollectionCommand.SEARCH;
import static java.util.Collections.unmodifiableList;

public class ApplicationCommands {

    private final AppController appController;

    private final Map<CollectionCommand, Command<Collection>> collectionCommandsMap = new HashMap<>();

    private final List<Command<Collection>> collectionCommandsList = new ArrayList<>();
    private final List<Command<Void>> leftToolBarCommands = new ArrayList<>();
    private final List<Command<Void>> rightToolBarCommands = new ArrayList<>();
    private final List<Command<Void>> fileMenuCommands = new ArrayList<>();
    private final List<Command<Void>> helpMenuCommands = new ArrayList<>();
    private final List<Command<Void>> collectionTreeContextMenuCommands = new ArrayList<>();

    @Inject
    public ApplicationCommands(AppController appController) {
        this.appController = appController;
        setupCommands();
    }

    private void setupCommands() {
        Command<Collection> create = new Command<>("New collection",
                "actions:folder-new",
                "create-collection",
                collection -> appController.openCreateCollectionDialog());

        Command<Collection> delete = new Command<>("Delete collection",
                "",
                "delete-collection",
                collection -> appController.deleteCollection(collection));

        Command<Collection> search = new Command<>("Search...",
                "actions:system-search",
                "search-collection",
                collection -> appController.searchCollection(collection));

        Command<Void> about = new Command<>("About LIRE-Lab",
                "status:dialog-information",
                "about",
                nullArg -> appController.showAboutDialog());

        Command<Void> home = new Command<>("Show Home",
                "actions:go-home",
                "show-home",
                nullArg -> appController.showHomeView());

        collectionCommandsMap.put(CREATE, create);
        collectionCommandsMap.put(DELETE, delete);
        collectionCommandsMap.put(SEARCH, search);

        collectionCommandsList.add(create);
        collectionCommandsList.add(search);
        collectionCommandsList.add(delete);

        fileMenuCommands.add(getAsVoid(create));

        helpMenuCommands.add(about);

        leftToolBarCommands.add(getAsVoid(create));
        leftToolBarCommands.add(about);

        rightToolBarCommands.add(home);

        collectionTreeContextMenuCommands.add(getAsVoid(create));
    }

    private Command<Void> getAsVoid(Command command) {
        Command<Void> result = command;
        return result;
    }

    public List<Command<Collection>> getCollectionCommands() {
        return unmodifiableList(collectionCommandsList);
    }

    public Command<Collection> getCollectionCommand(CollectionCommand type) {
        return collectionCommandsMap.get(type);
    }

    public List<Command<Void>> getLeftToolBarCommands() {
        return unmodifiableList(leftToolBarCommands);
    }

    public List<Command<Void>> getRightToolBarCommands() {
        return unmodifiableList(rightToolBarCommands);
    }

    public List<Command<Void>> getFileMenuCommands() {
        return unmodifiableList(fileMenuCommands);
    }

    public List<Command<Void>> getHelpMenuCommands() {
        return unmodifiableList(helpMenuCommands);
    }

    public List<Command<Void>> getCollectionTreeContextMenuCommands() {
        return unmodifiableList(collectionTreeContextMenuCommands);
    }

    public enum CollectionCommand {
        SEARCH,
        DELETE,
        CREATE
    }
}
