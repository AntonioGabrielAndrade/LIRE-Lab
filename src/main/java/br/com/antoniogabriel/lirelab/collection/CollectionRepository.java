package br.com.antoniogabriel.lirelab.collection;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CollectionRepository {

    private PathResolver resolver;

    @Inject
    public CollectionRepository(PathResolver resolver) {
        this.resolver = resolver;
    }

    public List<Collection> getCollections() {
        Path dir = Paths.get(resolver.getCollectionsPath());
        DirectoryStream.Filter<Path> filter = entry -> Files.isDirectory(entry);

        List<Collection> collections = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (Path path : stream) {
                CollectionXMLDAO dao = new CollectionXMLDAO(path.toFile());
                collections.add(dao.readCollection());
            }

        } catch (IOException | JAXBException e) {
            throw new LireLabException("Could not read collections directories", e);
        }

        return collections;
    }
}
