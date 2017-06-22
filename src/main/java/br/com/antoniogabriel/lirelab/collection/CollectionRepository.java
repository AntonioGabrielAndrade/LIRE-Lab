package br.com.antoniogabriel.lirelab.collection;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionRepository {

    private PathResolver resolver;

    @Inject
    public CollectionRepository(PathResolver resolver) {
        this.resolver = resolver;
    }

    public List<Collection> getCollections() {
        Path dir = Paths.get(resolver.getCollectionsPath());

        if(!Files.exists(dir)) return Collections.EMPTY_LIST;

        DirectoryStream.Filter<Path> filter = entry -> Files.isDirectory(entry);

        List<Collection> collections = new ArrayList<>();

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (Path path : stream) {
                CollectionXMLDAO dao = new CollectionXMLDAO(path.toFile());
                Collection collection = dao.readCollection();
                List<String> paths = getCollectionImagesPaths(collection);
                collection.setImagePaths(paths);
                collections.add(collection);
            }

        } catch (IOException | JAXBException e) {
            throw new LireLabException("Could not read collections directories", e);
        }

        return collections;
    }

    private List<String> getCollectionImagesPaths(Collection collection) {

        List<String> results = new ArrayList<>();

        try {
            IndexReader ir = DirectoryReader.open(FSDirectory.open(Paths.get(resolver.getIndexDirectoryPath(collection.getName()))));

            int num = ir.numDocs();
            for ( int i = 0; i < num; i++)
            {
                Document d = ir.document(i);
                results.add(d.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
            }
            ir.close();

        } catch (IOException e) {
            throw new LireLabException("Could not read index", e);
        }

        return results;
    }
}
