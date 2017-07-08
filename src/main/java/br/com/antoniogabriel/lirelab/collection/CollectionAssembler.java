package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CollectionAssembler {

    private PathResolver resolver;
    private CollectionUtils collectionUtils;

    @Inject
    public CollectionAssembler(PathResolver resolver, CollectionUtils collectionUtils) {
        this.resolver = resolver;
        this.collectionUtils = collectionUtils;
    }

    public Collection assembleCollectionFrom(Path collectionPath) throws JAXBException {
        CollectionXMLDAO dao = new CollectionXMLDAO(collectionPath.toFile());
        Collection collection = dao.readCollection();

        collection.setImages(getImagesOf(collection));

        return collection;
    }

    private List<Image> getImagesOf(Collection collection) {

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

}
