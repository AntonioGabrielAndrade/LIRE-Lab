package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.LireLabUtils;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CollectionRepository {

    private LireLabUtils lireLabUtils;
    private CollectionAssembler collectionAssembler;

    @Inject
    public CollectionRepository(LireLabUtils lireLabUtils, CollectionAssembler collectionAssembler) {
        this.lireLabUtils = lireLabUtils;
        this.collectionAssembler = collectionAssembler;
    }

    public List<Collection> getCollections() {
        return collectionsDirectoryExist() ?
                readCollectionsFromCollectionsDirectory() :
                emptyCollectionsList();
    }

    public Collection getCollection(String name) {
        try {
            return getCollectionIn(lireLabUtils.getCollectionPath(name));
        } catch (Exception e) {
            throw new LireLabException("Could not read collection", e);
        }
    }

    public void deleteCollection(String collectionName) {
        try {
            lireLabUtils.deleteCollection(collectionName);
        } catch (IOException e) {
            throw new LireLabException("Could not delete collection", e);
        }
    }

    private List<Collection> emptyCollectionsList() {
        return Collections.EMPTY_LIST;
    }

    private boolean collectionsDirectoryExist() {
        return lireLabUtils.collectionsDirectoryExist();
    }

    private List<Collection> readCollectionsFromCollectionsDirectory() {

        List<Collection> collections = new ArrayList<>();

        try(Subfolders subfolders = getSubfoldersOf(collectionsFolder())) {
            for (Path folder : subfolders) {
                try {
                    if(isCollection(folder)) {
                        collections.add(getCollectionIn(folder));
                    }
                } catch (UnmarshalException e) {
                    continue;
                }
            }

        } catch (IOException | JAXBException e) {
            throw new LireLabException("Could not read collections directories", e);
        }

        return collections;
    }

    private boolean isCollection(Path folder) {
        return lireLabUtils.isCollection(folder);
    }

    protected Subfolders getSubfoldersOf(Path dir) throws IOException {
        return Subfolders.of(dir);
    }

    private Path collectionsFolder() {
        return lireLabUtils.getCollectionsPath();
    }

    private Collection getCollectionIn(Path path) throws JAXBException, IOException {
        return collectionAssembler.assembleCollectionFrom(path);
    }

    public Set<String> getCollectionNames() {
        Set<String> names = new HashSet<>();

        try (Subfolders subfolders = getSubfoldersOf(collectionsFolder())) {
            for (Path folder : subfolders) {
                if (isCollection(folder)) {
                    names.add(folder.getFileName().toString());
                }
            }

        } catch (IOException e) {
            throw new LireLabException("Could not read collections names", e);
        }

        return names;
    }
}
