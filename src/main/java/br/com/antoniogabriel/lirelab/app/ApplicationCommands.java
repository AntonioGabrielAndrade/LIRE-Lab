package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.Collection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class ApplicationCommands {

    private AppController appController;

    private final Map<CollectionCommand, Command<Collection>> collectionCommandsMap = new HashMap<>();
    private final List<Command<Collection>> collectionCommandsList = new ArrayList<>();

    private final List<Command<Void>> leftToolBarCommands = new ArrayList<>();
    private final List<Command<Void>> rightToolBarCommands = new ArrayList<>();
    private final List<Command<Void>> fileMenuCommands = new ArrayList<>();
    private final List<Command<Void>> helpMenuCommands = new ArrayList<>();


    private final Map<GeneralCommand, Command<Void>> generalCommands = new HashMap<>();

    @Inject
    public ApplicationCommands(AppController appController) {
        this.appController = appController;

        setupCollectionCommands();
        setupGeneralCommands();
        setupLeftToolBarCommands();
        setupRightToolBarCommands();
        setupFileMenuCommands();
        setupHelpMenuCommands();
    }

    private void setupHelpMenuCommands() {
        Command<Void> about = new Command<>("About LIRE-Lab",
                "emblems:emblem-important",
                "about",
                nullArg -> appController.showAboutDialog());

        helpMenuCommands.add(about);
    }

    private void setupFileMenuCommands() {
        Command<Void> create = new Command<>("New collection",
                "actions:folder-new",
                "create-collection",
                collection -> appController.openCreateCollectionDialog());

        fileMenuCommands.add(create);
    }

    private void setupLeftToolBarCommands() {
        Command<Void> create = new Command<>("New collection",
                "actions:folder-new",
                "create-collection",
                collection -> appController.openCreateCollectionDialog());

        Command<Void> about = new Command<>("About LIRE-Lab",
                "emblems:emblem-important",
                "about",
                nullArg -> appController.showAboutDialog());

        leftToolBarCommands.add(create);
        leftToolBarCommands.add(about);
    }

    private void setupRightToolBarCommands() {
        Command<Void> home = new Command<>("Show Home",
                "actions:go-home",
                "show-home",
                nullArg -> appController.showHomeView());

        rightToolBarCommands.add(home);
    }

    private void setupCollectionCommands() {

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

        collectionCommandsMap.put(CollectionCommand.CREATE, create);
        collectionCommandsMap.put(CollectionCommand.DELETE, delete);
        collectionCommandsMap.put(CollectionCommand.SEARCH, search);

        collectionCommandsList.add(create);
        collectionCommandsList.add(search);
        collectionCommandsList.add(delete);
    }

    private void setupGeneralCommands() {
        Command<Void> about = new Command<>("About LIRE-Lab",
                                    "emblems:emblem-important",
                                    "about",
                                    nullArg -> appController.showAboutDialog());

        generalCommands.put(GeneralCommand.ABOUT, about);

    }

    public List<Command<Collection>> getCollectionCommands() {
        return unmodifiableList(collectionCommandsList);
    }

    public Command<Collection> getCollectionCommand(CollectionCommand type) {
        return collectionCommandsMap.get(type);
    }

    public Command getGeneralCommand(GeneralCommand type) {
        return generalCommands.get(type);
    }

    public List<Command<Void>> getLeftToolBarCommands() {
        return leftToolBarCommands;
    }

    public List<Command<Void>> getRightToolBarCommands() {
        return rightToolBarCommands;
    }

    public List<Command<Void>> getFileMenuCommands() {
        return fileMenuCommands;
    }

    public List<Command<Void>> getHelpMenuCommands() {
        return helpMenuCommands;
    }

    public enum CollectionCommand {
        SEARCH,
        DELETE,
        CREATE
    }

    public enum GeneralCommand {
        ABOUT
    }
}
