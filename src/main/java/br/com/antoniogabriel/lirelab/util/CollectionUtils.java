package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CollectionUtils {


    private PathResolver resolver;

    @Inject
    public CollectionUtils(PathResolver resolver) {
        this.resolver = resolver;
    }

    public List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), false);
    }
}
