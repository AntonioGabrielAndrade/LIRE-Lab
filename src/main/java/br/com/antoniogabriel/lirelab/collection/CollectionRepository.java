package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.File;
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
    private CollectionUtils collectionUtils;

    @Inject
    public CollectionRepository(PathResolver resolver, CollectionUtils collectionUtils) {
        this.resolver = resolver;
        this.collectionUtils = collectionUtils;
    }

    public List<Collection> getCollections() {
        if(collectionsDirectoryDontExist())
            return emptyCollectionsList();

        return readCollectionsFromCollectionsDirectory();
    }

    private List<Collection> emptyCollectionsList() {
        return Collections.EMPTY_LIST;
    }

    private boolean collectionsDirectoryDontExist() {
        return !Files.exists(collectionsPath());
    }

    private List<Collection> readCollectionsFromCollectionsDirectory() {

        List<Collection> collections = new ArrayList<>();

        Path dir = collectionsPath();

        DirectoryStream.Filter<Path> filter = entry -> Files.isDirectory(entry);

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (Path path : stream) {
                try {
                    collections.add(getCollection(path));
                } catch (UnmarshalException e) {
                    continue;
                }
            }

        } catch (IOException | JAXBException e) {
            throw new LireLabException("Could not read collections directories", e);
        }

        return collections;
    }

    protected Path collectionsPath() {
        return Paths.get(resolver.getCollectionsPath());
    }

    private Collection getCollection(Path path) throws JAXBException, IOException {
        CollectionXMLDAO dao = new CollectionXMLDAO(path.toFile());
        Collection collection = dao.readCollection();

        List<String> imagesPaths = getImagesPaths(collection);
        collection.setImagePaths(imagesPaths);

        List<String> thumbnailsPaths = getThumbnailPaths(collection);
        collection.setThumbnailPaths(thumbnailsPaths);

        collection.setImages(getImages(collection));

        return collection;
    }

    private List<String> getThumbnailPaths(Collection collection) throws IOException {
        return FileUtils.getAllImages(
                new File(resolver.getThumbnailsDirectoryPath(collection.getName())), false);
    }

    private List<String> getImagesPaths(Collection collection) {

        List<String> results = new ArrayList<>();

        try {
            IndexReader ir = DirectoryReader.open(FSDirectory.open(indexPath(collection)));

            int num = ir.numDocs();
            for ( int i = 0; i < num; i++)
            {
                Document d = ir.document(i);
                String imagePath = d.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
                results.add(imagePath);
            }
            ir.close();

        } catch (IOException e) {
            throw new LireLabException("Could not read index", e);
        }

        return results;
    }

    private List<Image> getImages(Collection collection) {

        List<Image> results = new ArrayList<>();

        try {
            IndexReader ir = DirectoryReader.open(FSDirectory.open(indexPath(collection)));

            int num = ir.numDocs();
            for ( int i = 0; i < num; i++)
            {
                Document d = ir.document(i);
                String imagePath = d.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
                String thumbnailPath = collectionUtils.getThumbnailPathFromImagePath(collection, imagePath);
                results.add(new Image(imagePath, thumbnailPath));
            }
            ir.close();

        } catch (IOException e) {
            throw new LireLabException("Could not read index", e);
        }

        return results;
    }

    private Path indexPath(Collection collection) {
        return Paths.get(resolver.getIndexDirectoryPath(collection.getName()));
    }

    public Collection getCollection(String name) {
        try {
            return getCollection(Paths.get(resolver.getCollectionPath(name)));
        } catch (Exception e) {
            throw new LireLabException("Could not read collection", e);
        }
    }
}
