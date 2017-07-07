package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QueryRunner {

    private PathResolver resolver;

    public QueryRunner(PathResolver resolver) {
        this.resolver = resolver;
    }

    public Collection runQuery(Collection collection, Feature feature, Image queryImage) throws IOException {
        BufferedImage img = null;
        File f = new File(queryImage.getImagePath());
        if (f.exists()) {
            try {
                img = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        IndexReader ir = DirectoryReader.open(FSDirectory.open(Paths.get(resolver.getIndexDirectoryPath(collection.getName()))));
        ImageSearcher searcher = new GenericFastImageSearcher(collection.getImages().size(), feature.getLireClass());

        List<Image> result = new ArrayList<>();
        CollectionUtils utils = new CollectionUtils(resolver);

        // searching with a image file ...
        ImageSearchHits hits = searcher.search(img, ir);
        // searching with a Lucene document instance ...
        for (int i = 0; i < hits.length(); i++) {
            String fileName = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            Image image = new Image(fileName, utils.getThumbnailPathFromImagePath(collection, fileName));
            result.add(image);
            System.out.println(hits.score(i) + ": \t" + fileName);
        }

        collection.setImages(result);
        return collection;
    }
}
